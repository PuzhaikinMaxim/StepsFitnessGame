package com.puj.stepsfitnessgame.presentation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.puj.stepsfitnessgame.data.StepCountingWorker
import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "step_counting_channel",
            "step_count",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
        ApplicationContextProvider.setApplicationContext(this)
    }
}