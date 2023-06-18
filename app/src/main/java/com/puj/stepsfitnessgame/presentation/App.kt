package com.puj.stepsfitnessgame.presentation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.work.Configuration


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "step_counting_channel",
            "step_count",
            NotificationManager.IMPORTANCE_HIGH
        )

        val channel2 = NotificationChannel(
            "channel_2",
            "name_2",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
        notificationManager.createNotificationChannel(channel2)
        ApplicationContextProvider.setApplicationContext(this)
    }
}