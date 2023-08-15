package com.example.notificationtimer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.time.LocalDateTime
import java.util.Timer
import java.util.TimerTask

class SecondForegroundService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        updateNotification()

        return START_NOT_STICKY
    }

    private fun updateNotification(){
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                val clock = LocalDateTime.now().second
                startForeground(222, notification("second: $clock"))
            }
        },0, 1000)
    }

    private fun notification(contentText: String) : Notification {
        val title = "Second Notification Timer"
//        val contentText = "ForegroundService is active"
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

fun startNotifySecondForegroundService(context: Context) {
    ContextCompat.startForegroundService(context, Intent(context, SecondForegroundService::class.java))
}

fun stopNotifySecondForegroundService(context: Context) {
    val targetIntent = Intent(context, SecondForegroundService::class.java)
    context.stopService(targetIntent)
}