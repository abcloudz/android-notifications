package com.abcloudz.notif.extensions

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.abcloudz.notif.InteractReceiver
import java.util.Random

val randomInt: Int
    get() = Random().nextInt()

inline fun <reified T : Any> Context.createAlarmIntent(notificationId: Int, repeatedCount: Int) =
    Intent(this, T::class.java).apply {
        putExtra(InteractReceiver.KEY_NOTIFICATION_ID, notificationId)
        putExtra(InteractReceiver.KEY_REPEATED_COUNT, repeatedCount)
    }

inline fun <reified T : Any> Context.pendingBroadcastAlarmIntent(notificationId: Int, repeatedCount: Int) =
    pendingBroadcastIntent(
        requestCode = notificationId,
        intent = createAlarmIntent<T>(notificationId, repeatedCount)
    )

fun Context.pendingBroadcastIntent(requestCode: Int, intent: Intent) =
    PendingIntent.getBroadcast(
        this,
        requestCode,
        intent,
        pendingFlags()
    )

fun pendingFlags() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
else PendingIntent.FLAG_UPDATE_CURRENT