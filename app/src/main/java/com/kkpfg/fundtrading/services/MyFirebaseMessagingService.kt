package com.kkpfg.fundtrading.services

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kkpfg.fundtrading.R
import com.kkpfg.fundtrading.view.activities.NotificationReaderActivity

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title ?: ""
        val content = message.notification?.body ?: ""

        val payload = message.data

        createNotification(title, content)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("PUSH_NOTI", "Token = $token")
    }

    private fun createNotification(title: String, content: String){
        val CHANNEL_ID = "ch1"

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content)
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notiManager = NotificationManagerCompat.from(this)

        val intent = Intent(this, NotificationReaderActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("content", content)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent)

        val granted = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        if (granted) {
            notiManager.notify(10, builder.build())
        }
    }

}