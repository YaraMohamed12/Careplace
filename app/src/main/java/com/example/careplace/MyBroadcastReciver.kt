package com.example.careplace

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.NotificationCompat

class MyBroadcastReciver: BroadcastReceiver() {

    private var mediaPlayer: MediaPlayer? = null


    override fun onReceive(context: Context?, intent: Intent?) {

        mediaPlayer = MediaPlayer.create(context, R.raw.notification_sound)
        mediaPlayer?.start()
        // Create an explicit intent for AlarmingScreen
        val notificationIntent = Intent(context, AlarmingScreen::class.java)
        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create the notification channel (required for Android Oreo and higher)
        val channelId = "your_channel_id"
        val channelName = "Alarm Notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        createNotificationChannel(context!!, channelId, channelName, importance)

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alarm Notification")
            .setContentText("Click to open AlarmingScreen")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Notify using the NotificationManager
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(123, notificationBuilder.build())
    }

    private fun createNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String,
        importance: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager =
                context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


}