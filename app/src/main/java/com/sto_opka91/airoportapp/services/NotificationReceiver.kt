package com.sto_opka91.airoportapp.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.sto_opka91.airoportapp.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        createNotificationChannel(context)
        showNotification(context)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationRepository.CHANNEL_ID,
                "Push Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for push notifications"
                enableLights(true)
                enableVibration(true)
                setShowBadge(true)
                importance = NotificationManager.IMPORTANCE_HIGH // Явно указываем важность
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(context: Context) {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_logo)

        // Создаем Intent для открытия приложения при нажатии на уведомление
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, NotificationRepository.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setLargeIcon(bitmap)
            .setContentTitle("Уведомления включены")
            .setContentText("Здесь будет ваше пуш-уведомление")
            .setPriority(NotificationCompat.PRIORITY_MAX) // Изменено на MAX
            .setCategory(NotificationCompat.CATEGORY_ALARM) // Изменено на ALARM
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setVibrate(longArrayOf(0, 1000, 500, 1000))
            .setLights(Color.BLUE, 1000, 1000)
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            // Добавляем действие
            .addAction(
                R.drawable.ic_notification,
                "Открыть",
                pendingIntent
            )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NotificationRepository.NOTIFICATION_ID, builder.build())
    }
}