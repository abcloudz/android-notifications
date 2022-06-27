package com.abcloudz.notif.extensions

import android.app.AlarmManager
import android.content.Context
import com.abcloudz.notif.AlarmReceiver
import java.util.Calendar

val Context.alarmManager: AlarmManager
    get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager

fun Context.scheduleExact(requestCode: Int, calendar: Calendar, repeatedCount: Int) {
    val pendingIntent = pendingBroadcastAlarmIntent<AlarmReceiver>(requestCode, repeatedCount)
    alarmManager.run {
        cancel(pendingIntent)
        setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}