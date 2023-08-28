#!/bin/sh


KEYSTORE_FILE="../cert/cert.keystore"
KEY_ALIAS="kkpfg"
CERT_PASSWORD="123456"
KEY_PASSWORD="123456"

LINE_NOTIFY_TOKEN="AXkQuu5UyY0Wve08SveoR02uUfNtrk6QcxazefeKW47"

function unitTest(){
    ./gradlew test
    # Capture the exit code of the previous command
    exit_code=$?
    # Check the exit code and provide feedback
    if [ $exit_code -eq 0 ]; then
        echo "Tests passed successfully!"
        buildAPK
    else
        echo "Tests failed!"
        exit $exit_code
    fi
}

function buildAPK(){
    rm -rf "./app/build"
    ./gradlew assembleDeviceRelease

    file_path="./app/build/outputs/apk/device/release/app-device-release-unsigned.apk"

    if [ -e "$file_path" ]; then
        #Build Success
        cp "$file_path" "./build/" 

        alignAndSign
    fi

}

function alignAndSign(){
    zipalign -v -p 4 ./build/app-device-release-unsigned.apk  ./build/aligned.apk
    
    apksigner sign \
    --ks "$KEYSTORE_FILE" \
    --ks-key-alias "$KEY_ALIAS" \
    --ks-pass "pass:$CERT_PASSWORD" \
    --key-pass "pass:$KEY_PASSWORD" \
    --out "./build/release.apk"      "./build/aligned.apk"

    curl -X POST -F "file=@./build/release.apk" http://192.168.68.56:3000/upload-apk
    curl -X POST -H "Authorization: Bearer $LINE_NOTIFY_TOKEN" -F "message=New APK AvailableNow At http://192.168.68.55:3000/apk" https://notify-api.line.me/api/notify
}


unitTest


