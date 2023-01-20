package com.puj.stepsfitnessgame.data

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.puj.stepsfitnessgame.R
import kotlinx.coroutines.delay

class StepCountingWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        startForegroundService()
        for (i in 0..1000){
            delay(1000)
            println(i)
        }
        return Result.success(
            workDataOf(
                "test" to "test"
            )
        )
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                ID,
                NotificationCompat.Builder(context, "step_counting_channel")
                    .setContentText("Test")
                    .setContentTitle("Test")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .build()
            )
        )
    }

    companion object {
        private const val ID = 1
    }
}