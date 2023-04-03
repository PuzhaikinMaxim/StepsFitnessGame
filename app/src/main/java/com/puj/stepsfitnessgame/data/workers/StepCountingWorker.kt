package com.puj.stepsfitnessgame.data.workers

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase
import com.puj.stepsfitnessgame.data.stepactivity.StepActivityDataSource
import kotlinx.coroutines.delay

class StepCountingWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    init {
        FitnessGameDatabase.initializeDatabase(context)
    }

    private val stepActivityDataSource: StepActivityDataSource = StepActivityDataSource

    //private val challengeRemoteDataSource = ChallengeRemoteDataSourceImpl()

    //private val dailyTaskRemoteDataSourceImpl = DailyTaskRemoteDataSourceImpl()

    override suspend fun doWork(): Result {
        startForegroundService()
        var i = 0
        while (true) {
            delay(THREE_MINUTES)
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
        private const val THREE_MINUTES = 3 * 60 * 1000L
    }
}