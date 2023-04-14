package com.puj.stepsfitnessgame.data.stepactivity

import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase
import com.puj.stepsfitnessgame.data.database.laststepcount.LastStepCountUpdateDbModel
import com.puj.stepsfitnessgame.data.network.stepactivity.GoogleFitDataProvider
import com.puj.stepsfitnessgame.domain.models.stepstatistics.TodayStatistics
import com.puj.stepsfitnessgame.domain.models.stepstatistics.StepData
import java.time.LocalDateTime

object StepActivityDataSource {

    private val stepActivityDataProvider: StepActivityDataProvider by lazy {
        GoogleFitDataProvider()
    }

    private val userGoalDao = FitnessGameDatabase.getDatabase().goalDao()

    private val lastStepCountDao = FitnessGameDatabase.getDatabase().lastStepCountUpdateDao()

    suspend fun getStepCountInInterval(): Int {
        val start = lastStepCountDao.getLastUpdate()?.lastStepCountUpdate ?: LocalDateTime.now()
        val end = LocalDateTime.now()
        return stepActivityDataProvider.getStepCountInInterval(start, end)
    }

    suspend fun getTodayStatistics(): TodayStatistics {
        val todayStepAmount = stepActivityDataProvider.getTodayStepCount()
        val todayMeterPassed = stepActivityDataProvider.getTodayMetersPassed()
        val todayGoal = userGoalDao.getGoal()?.goal ?: 500
        return TodayStatistics(
            todayStepAmount,
            todayMeterPassed,
            todayGoal
        )
    }

    suspend fun getLastSevenDaysStatistics(): List<StepData> {
        return stepActivityDataProvider.getLastDaysStatistics(7)
    }

    suspend fun getLastTwelveMonthStatistics(): List<StepData> {
        return stepActivityDataProvider.getLastMonthsStatistics(12)
    }

    suspend fun getLastTwelveWeeksStatistics(): List<StepData> {
        return stepActivityDataProvider.getLastWeeksStatistics(12)
    }

    suspend fun getLastThirtyDaysStatistics(): List<StepData> {
        return stepActivityDataProvider.getLastDaysStatistics(30)
    }

    suspend fun updateLastStepCount() {
        val lastUpdate = LastStepCountUpdateDbModel(
            lastStepCountUpdate = LocalDateTime.now()
        )
        lastStepCountDao.setLastStepCountUpdate(lastUpdate)
    }
}