package com.kkpfg.fundtrading.data.sqlite

import android.content.Context
import com.kkpfg.fundtrading.utils.secret.Crypto

class TokenDatasource(context: Context) {
    val dbHelper = DatabaseHelper(context)

    fun deleteAllToken(){
        val db = dbHelper.writableDatabase
        val query = "DELETE FROM token;"
        return db.execSQL(query)
    }

    fun saveToken(accessToken: String, refreshToken: String){
        deleteAllToken()

        val db = dbHelper.writableDatabase
        val query = "INSERT INTO token (accessToken, refreshToken) VALUES (?, ?)"

        val crypto = Crypto.getInstance()
        val encryptedAccessToken = crypto.encrypt(accessToken)
        val encryptedRefreshToken = crypto.encrypt(refreshToken)

        val values = arrayOf(encryptedAccessToken, encryptedRefreshToken)
        return db.execSQL(query, values)
    }

    fun getAccessToken(): String?{
        val db = dbHelper.readableDatabase

        val tokens = mutableListOf<String>()

        val query = "SELECT accessToken FROM token LIMIT 1"

        val cursor = db.rawQuery(query, null)

        with(cursor) {
            while (moveToNext()) {
                val token = getString(getColumnIndexOrThrow("accessToken"))
                tokens.add(token)
            }
        }

        if(tokens.size > 0){
            val crypto = Crypto.getInstance()
            return crypto.decrypt(tokens[0])
        }

        return null
    }

    fun getRefreshToken(): String?{
        val db = dbHelper.readableDatabase

        val tokens = mutableListOf<String>()
        val query = "SELECT refreshToken FROM token LIMIT 1"
        val cursor = db.rawQuery(query, null)

        with(cursor) {
            while (moveToNext()) {
                val taskName = getString(getColumnIndexOrThrow("refreshToken"))
                tokens.add(taskName)
            }
        }

        if(tokens.size > 0){
            val crypto = Crypto.getInstance()
            return crypto.decrypt(tokens[0])
        }
        return null
    }
}