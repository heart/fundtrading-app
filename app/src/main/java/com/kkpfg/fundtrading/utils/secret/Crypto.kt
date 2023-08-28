package com.kkpfg.fundtrading.utils.secret

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class Crypto {
    companion object{
        const val KEY_ALIAS = "MAIN_KEY"

        private var _instance: Crypto? = null

        fun getInstance(): Crypto{
            if(_instance == null){
                _instance = Crypto()
            }
            return _instance!!
        }
    }

    private var keyStore:KeyStore? = null

    init{
        keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore?.load(null)

        if (!keyStore!!.containsAlias(KEY_ALIAS)) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setRandomizedEncryptionRequired(false) // ECB mode doesn't need this
                .build()

            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }
    }

    fun encrypt(data: String): String {
        val key = keyStore?.getKey(KEY_ALIAS, null) as SecretKey
        val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedData = cipher.doFinal(data.toByteArray())

        return Base64.encodeToString(encryptedData, Base64.DEFAULT)
    }

    fun decrypt(encryptedDataString: String): String {
        val key = keyStore?.getKey(KEY_ALIAS, null) as SecretKey
        val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
        cipher.init(Cipher.DECRYPT_MODE, key)

        val encryptedData = Base64.decode(encryptedDataString, Base64.DEFAULT)
        val decryptedData = cipher.doFinal(encryptedData)

        return String(decryptedData, Charset.forName("UTF-8"))
    }
}