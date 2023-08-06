package com.example.notificationtimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            makeNotification(
                "Notification Timer", "Notification after 10 seconds", context!!
            )
        }catch (e: Exception){
            Log.d(TAG, e.printStackTrace().toString())
        }
    }

    companion object {
        const val TAG = "AlarmReceiverDebug"
    }
}