package com.abcloudz.notif

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.abcloudz.notif.extensions.notificationManager
import com.abcloudz.notif.extensions.scheduleExact
import java.util.Calendar
import java.util.concurrent.TimeUnit

class InteractReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val delayMin: Long = when (intent.action) {
            REMIND_1 -> 1
            REMIND_5 -> 5
            REMIND_15 -> 15
            REMIND_30 -> 30
            else -> null
        } ?: return

        val calendar = Calendar.getInstance().apply {
            val delayMillis = TimeUnit.MINUTES.toMillis(delayMin)
            timeInMillis = System.currentTimeMillis() + delayMillis
        }

        val notificationId = intent.getIntExtra(KEY_NOTIFICATION_ID, 0)
        context.notificationManager.cancel(notificationId)

        val repeatedCount = intent.getIntExtra(KEY_REPEATED_COUNT, 0) + 1
        context.scheduleExact(notificationId, calendar, repeatedCount)
    }

    companion object {
        val REMIND_1 = "com.abcloudz.notif.REMIND_1"
        val REMIND_5 = "com.abcloudz.notif.REMIND_5"
        val REMIND_15 = "com.abcloudz.notif.REMIND_15"
        val REMIND_30 = "com.abcloudz.notif.REMIND_30"
        val KEY_NOTIFICATION_ID = "NOTIFICATION_ID"
        val KEY_REPEATED_COUNT = "REPEATED_COUNT"
    }
}