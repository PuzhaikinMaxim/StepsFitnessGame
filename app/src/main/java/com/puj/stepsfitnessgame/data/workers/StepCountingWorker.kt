package com.puj.stepsfitnessgame.data.workers

import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase
import com.puj.stepsfitnessgame.data.network.challenge.ChallengeRemoteDataSourceImpl
import com.puj.stepsfitnessgame.data.network.dailychallenge.DailyChallengeRemoteDataSourceImpl
import com.puj.stepsfitnessgame.data.network.duel.DuelRemoteDataSourceImpl
import com.puj.stepsfitnessgame.data.network.guildchallenge.GuildChallengeDataSourceImpl
import com.puj.stepsfitnessgame.data.network.playerstatistics.PlayerStatisticsDataSourceImpl
import com.puj.stepsfitnessgame.data.stepactivity.StepActivityDataSource
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import kotlinx.coroutines.delay

class StepCountingWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    init {
        FitnessGameDatabase.initializeDatabase(context)
    }

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(context)
    }

    private val token = context.getSharedPreferences(
        PreferencesValues.PREFERENCES_KEY,
        Context.MODE_PRIVATE
    ).getString(
        TOKEN_KEY,
        DEFAULT
    ) ?: DEFAULT

    private val stepActivityDataSource: StepActivityDataSource = StepActivityDataSource

    private val duelRemoteDataSource = DuelRemoteDataSourceImpl(token)

    private val dailyChallengeRemoteDataSource = DailyChallengeRemoteDataSourceImpl(token)

    private val guildChallengeRemoteDataSource = GuildChallengeDataSourceImpl(token)

    private val challengeRemoteDataSource = ChallengeRemoteDataSourceImpl(token)

    private val playerStatisticsDataProvider = PlayerStatisticsDataSourceImpl(token)

    override suspend fun doWork(): Result {
        startForegroundService()
        var i = 0
        while (true) {
            delay(FIVE_SECONDS)
            //val stepCount = stepActivityDataSource.getStepCountInInterval()
            val stepCount = 10
            try {
                challengeRemoteDataSource.updateChallengeProgress(stepCount)
                duelRemoteDataSource.updateStepAmount(stepCount)
                dailyChallengeRemoteDataSource.updateDailyChallengesProgress(stepCount)
                guildChallengeRemoteDataSource.updateProgress(stepCount)
                playerStatisticsDataProvider.updateProgress(stepCount)
                stepActivityDataSource.updateLastStepCount()
                Intent("step_count_updated").apply {
                    localBroadcastManager.sendBroadcast(this)
                }
            }
            catch (ex: Exception){
                
            }
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
        private const val TOKEN_KEY = "authToken"
        private const val DEFAULT = "default"
        private const val ID = 1
        private const val FIVE_SECONDS = 5 * 1000L
        private const val THREE_MINUTES = 3 * 60 * 1000L
    }
}