package com.kkpfg.fundtrading.utils
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class UtilityFunctions {
    companion object {
        fun getRealPathFromUri(context: Context, uri: Uri): String? {
            val contentResolver: ContentResolver = context.contentResolver
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = null
            try {
                cursor = contentResolver.query(uri, projection, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    return cursor.getString(columnIndex)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        fun createFileFromUri(context: Context, uri: Uri): File? {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null

            val outputFile = File(context.cacheDir, "temp_file_${System.currentTimeMillis()}.jpg")

            try {
                val outputStream = FileOutputStream(outputFile)
                inputStream.copyTo(outputStream)
                outputStream.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

            return outputFile
        }
    }
}
