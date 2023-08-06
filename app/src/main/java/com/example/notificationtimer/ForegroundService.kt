package com.example.notificationtimer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class ForegroundService: Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        startForeground(222, notification())

        return START_STICKY
    }

    private fun notification() : Notification {
        val title = "Notification Timer"
        val contentText = "ForegroundService is active"
        val channelId = CHANNEL_ID
        val channelName = "Foreground Channel"
        val channel = NotificationChannel(
            channelId, channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setOngoing(true)

        notification.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        return notification.build()
    }
}

fun startNotifyForegroundService(context: Context) {
    ContextCompat.startForegroundService(context, Intent(context, ForegroundService::class.java))
}

fun stopNotifyForegroundService(context: Context) {
    val targetIntent = Intent(context, ForegroundService::class.java)
    context.stopService(targetIntent)
}