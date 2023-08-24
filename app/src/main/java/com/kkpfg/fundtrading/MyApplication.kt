package com.kkpfg.fundtrading

import TokenManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("PUSH_NOTI", "TOKEN=$token")
            }
        }

        TokenManager.initTokenManager(this)
        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        val name = getString(R.string.push_noti_channel_name)
        val descriptionText = getString(R.string.push_noti_channel_desctibe)
        val CHANNEL_ID = "ch1"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(channel)
    }

}