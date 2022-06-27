package com.abcloudz.notif

import android.content.Context
import android.content.Intent
import androidx.legacy.content.WakefulBroadcastReceiver
import com.abcloudz.notif.extensions.createImportantNotification

class AlarmReceiver : WakefulBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(InteractReceiver.KEY_NOTIFICATION_ID, 0)
        val repeatedCount = intent.getIntExtra(InteractReceiver.KEY_REPEATED_COUNT, 0)
        context.createImportantNotification(
            title = "Id: $notificationId",
            text = "Notification id: $notificationId \n Repeated times: $repeatedCount",
            notificationId = notificationId,
            repeatedCount = repeatedCount
        )
    }
}