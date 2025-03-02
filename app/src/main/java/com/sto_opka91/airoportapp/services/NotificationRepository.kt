package com.sto_opka91.airoportapp.services

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification() {
        if (alarmManager == null) return

        try {
            val intent = Intent(context, NotificationReceiver::class.java)
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                NOTIFICATION_ID,
                intent,
                flags
            )

            val triggerTime = System.currentTimeMillis() + 3000

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                } else {
                    // Если нет разрешения на точные будильники, используем неточный
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        } catch (e: Exception) {
            Log.e("NotificationRepository", "Error scheduling notification", e)
        }
    }

    fun cancelNotification() {
        if (alarmManager == null) return

        try {
            val intent = Intent(context, NotificationReceiver::class.java)
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                NOTIFICATION_ID,
                intent,
                flags
            )
            alarmManager.cancel(pendingIntent)
        } catch (e: Exception) {
            Log.e("NotificationRepository", "Error canceling notification", e)
        }
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "push_notification_channel"
    }
}