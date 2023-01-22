package com.puj.stepsfitnessgame.data

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase
import com.puj.stepsfitnessgame.data.network.stepactivity.GoogleFitDataProvider
import com.puj.stepsfitnessgame.data.network.stepactivity.StepActivityDataProvider
import com.puj.stepsfitnessgame.data.network.stepactivity.StepActivityDataSource
import kotlinx.coroutines.delay

class StepCountingWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    private val lastStepCountUpdateDao = FitnessGameDatabase.getDatabase(context).taskDao()
    private val stepActivityDataSource: StepActivityDataSource = StepActivityDataSource()

    override suspend fun doWork(): Result {
        startForegroundService()
        var i = 0
        while (true) {
            delay(1000)
            println(i)
            i++
        }
        val ld = MutableLiveData<Int>()
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
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .build()
            )
        )
    }

    companion object {
        private const val ID = 1
    }
}