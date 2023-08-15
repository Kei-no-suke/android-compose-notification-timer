package com.example.notificationtimer

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.notificationtimer.ui.theme.NotificationTimerTheme
import java.time.LocalDateTime
import java.time.ZoneId

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        "FCM can't post notifications without POST_NOTIFICATIONS permission",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        setContent {
            NotificationTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotificationScreen(this)
                }
            }
        }
    }
}

@Composable
fun NotificationScreen(context: Context, modifier: Modifier = Modifier) {
    val scheduler = AlarmScheduler(context)
    Column {
        Button(onClick = { makeNotification("Notification Timer", "Notification", context) }) {
            Text("Notification")
        }
        Button(onClick = { startNotifyForegroundService(context) }) {
            Text("StartForegroundService")
        }
        Button(onClick = { stopNotifyForegroundService(context) }) {
            Text("StopForegroundService")
        }
        Button(onClick = { startNotifySecondForegroundService(context) }) {
            Text("StartSecondForegroundService")
        }
        Button(onClick = { stopNotifySecondForegroundService(context) }) {
            Text("StopSecondForegroundService")
        }
        Button(onClick = { scheduler.schedule(
            LocalDateTime.now()
            .plusSeconds(10).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
        ) })
        {
            Text("Notify after 10 seconds")
        }
    }
}
