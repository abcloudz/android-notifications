package com.abcloudz.notif.extensions

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abcloudz.notif.InteractReceiver
import com.abcloudz.notif.R

private const val IMPORTANT_CHANNEL = "IChannel"

private val vibration = longArrayOf(200, 200, 200)

val Context.notificationManager: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

fun Context.createChannel(channelId: String) {
    val att = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
        .build()

    val channel = NotificationChannelCompat.Builder(channelId, NotificationManager.IMPORTANCE_HIGH)
        .setVibrationEnabled(true)
        .setName("ui")
        .setSound(generateUri(R.raw.time_x), att)
        .setVibrationPattern(vibration)
        .setShowBadge(true)
        .build()

    notificationManager.createNotificationChannel(channel)
}

fun Context.createImportantNotification(title: String, text: String, notificationId: Int, repeatedCount: Int) {
    createNotification(IMPORTANT_CHANNEL, title, text, notificationId, repeatedCount)
}

fun Context.createNotification(
    channelId: String,
    title: String,
    text: String,
    notificationId: Int,
    repeatedCount: Int
) {
    createChannel(channelId)
    val notification = NotificationCompat.Builder(this, channelId)
        .setContentTitle(title)
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_star)
        .setAutoCancel(true)
        .setContentIntent(actionPendingIntent(InteractReceiver.REMIND_1, notificationId, repeatedCount))
        .addAction(notificationAction("5 min", InteractReceiver.REMIND_5, notificationId, repeatedCount))
        .addAction(notificationAction("15 min", InteractReceiver.REMIND_15, notificationId, repeatedCount))
        .addAction(notificationAction("30 min", InteractReceiver.REMIND_30, notificationId, repeatedCount))
        .build()

    notificationManager.notify(notificationId, notification)
}

fun Context.notificationAction(
    name: String,
    action: String,
    notificationId: Int,
    repeatedCount: Int
): NotificationCompat.Action {
    val pendingIntent = actionPendingIntent(action, notificationId, repeatedCount)
    return NotificationCompat.Action.Builder(R.drawable.ic_double_arrow, name, pendingIntent)
        .build()
}

private fun Context.actionPendingIntent(
    action: String,
    notificationId: Int,
    repeatedCount: Int
): PendingIntent {
    val intent = Intent(action).apply {
        setPackage(this@actionPendingIntent.packageName)
        putExtra(InteractReceiver.KEY_NOTIFICATION_ID, notificationId)
        putExtra(InteractReceiver.KEY_REPEATED_COUNT, repeatedCount)
    }
    return PendingIntent.getBroadcast(this, randomInt, intent, pendingFlags())
}