package com.sto_opka91.airoportapp.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.sto_opka91.airoportapp.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            createNotificationChannel(context)
            showNotification(context)
        } catch (e: Exception) {
            Log.e("NotificationReceiver", "Error showing notification", e)
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val channel = NotificationChannel(
                    NotificationRepository.CHANNEL_ID,
                    "Push Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Channel for push notifications"
                    enableLights(true)
                    enableVibration(true)
                    setShowBadge(true)
                    importance = NotificationManager.IMPORTANCE_HIGH
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                }

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                notificationManager?.createNotificationChannel(channel)
            } catch (e: Exception) {
                Log.e("NotificationReceiver", "Error creating notification channel", e)
            }
        }
    }

    private fun showNotification(context: Context) {
        try {
            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_logo)


            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                flags
            )

            val builder = NotificationCompat.Builder(context, NotificationRepository.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(bitmap)
                .setContentTitle("Уведомления включены")
                .setContentText("Здесь будет ваше пуш-уведомление")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)




            builder.addAction(
                R.drawable.ic_notification,
                "Открыть",
                pendingIntent
            )


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                builder.setFullScreenIntent(pendingIntent, true)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.notify(NotificationRepository.NOTIFICATION_ID, builder.build())
        } catch (e: Exception) {
            Log.e("NotificationReceiver", "Error showing notification", e)
        }
    }
}