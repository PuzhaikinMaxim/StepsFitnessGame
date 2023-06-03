package com.puj.stepsfitnessgame.data.workers

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
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
import com.puj.stepsfitnessgame.domain.models.stepstatistics.TodayStatistics
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

    private lateinit var notificationBuilder: Builder

    private val stepActivityDataSource: StepActivityDataSource = StepActivityDataSource

    private val duelRemoteDataSource = DuelRemoteDataSourceImpl(token)

    private val dailyChallengeRemoteDataSource = DailyChallengeRemoteDataSourceImpl(token)

    private val guildChallengeRemoteDataSource = GuildChallengeDataSourceImpl(token)

    private val challengeRemoteDataSource = ChallengeRemoteDataSourceImpl(token)

    private val playerStatisticsDataProvider = PlayerStatisticsDataSourceImpl(token)

    override suspend fun doWork(): Result {
        createNotificationBuilder()
        while (true) {
            delay(FIFTEEN_SECONDS)
            val todayStatistics = stepActivityDataSource.getTodayStatistics()
            notificationBuilder.setCustomContentView(createCustomContentView(todayStatistics))
            startForegroundService(notificationBuilder)
            //val stepCount = stepActivityDataSource.getStepCountInInterval()
            //if(stepCount == 0) continue
            val stepCount = 1000
            try {
                challengeRemoteDataSource.updateChallengeProgress(stepCount)
                duelRemoteDataSource.updateStepAmount(stepCount)
                updateDailyChallengeList(stepCount)
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

    private suspend fun updateDailyChallengeList(stepCount: Int) {
        val shouldUpdateDailyChallengeList =
            dailyChallengeRemoteDataSource.updateDailyChallengesProgress(stepCount)
        if(shouldUpdateDailyChallengeList) {
            dailyChallengeRemoteDataSource.generateDailyChallengeList()
        }
    }

    private suspend fun startForegroundService(notificationBuilder: Builder) {
        setForeground(
            ForegroundInfo(
                ID,
                notificationBuilder.build()
            )
        )
    }

    private fun createCustomContentView(
        todayStatistics: TodayStatistics?
    ): RemoteViews {
        val contentView = RemoteViews(context.packageName, R.layout.layout_notification)
        if(todayStatistics == null) return contentView
        contentView.setTextViewText(R.id.tv_amount_of_steps_today, context.getString(
            R.string.statistics_amount_of_steps_passed,
            todayStatistics.stepAmount,
            todayStatistics.kilometersPassed
        ))
        contentView.setTextViewText(R.id.tv_percent_of_goal_completed, context.getString(
            R.string.statistics_percent_of_completion,
            todayStatistics.percentOfGoal
        ))
        contentView.setProgressBar(
            R.id.pb_daily_step_count_progress,
            todayStatistics.goal,
            todayStatistics.stepAmount,
            false
        )
        return contentView
    }

    private fun createNotificationBuilder(): Builder {
        notificationBuilder = Builder(context, "step_counting_channel")
            .setContentTitle("Шаги RPG. Фитнес игра с шагомером")
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setSilent(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setSmallIcon(R.drawable.ic_dungeon_white_transparent)
        return notificationBuilder
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val DEFAULT = "default"
        private const val ID = 1
        private const val FIFTEEN_SECONDS = 3 * 1000L
        private const val THREE_MINUTES = 3 * 60 * 1000L
    }
}