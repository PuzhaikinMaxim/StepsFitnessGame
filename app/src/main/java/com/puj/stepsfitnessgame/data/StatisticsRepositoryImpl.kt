package com.puj.stepsfitnessgame.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase
import com.puj.stepsfitnessgame.data.network.stepactivity.StepActivityDataSource
import com.puj.stepsfitnessgame.domain.StatisticsRepository
import com.puj.stepsfitnessgame.domain.models.statistics.StepData
import com.puj.stepsfitnessgame.domain.models.statistics.TodayStatistics

class StatisticsRepositoryImpl: StatisticsRepository {

    private val stepActivityDataSource = StepActivityDataSource()

    private val stepData = MutableLiveData<List<StepData>>()

    private val todayStatistics = MutableLiveData<TodayStatistics>()

    private val userGoalDao = FitnessGameDatabase.getDatabase().goalDao()

    override fun getTodayStatistics(): LiveData<TodayStatistics> {
        return todayStatistics
    }

    override suspend fun setTodayStatistics() {
        todayStatistics.postValue(stepActivityDataSource.getTodayStatistics())
    }

    override fun getLastDatesStepData(): LiveData<List<StepData>> {
        return stepData
    }

    override suspend fun setLastTwelveMonthStepData() {
        stepData.postValue(stepActivityDataSource.getLastTwelveMonthStatistics())
    }

    override suspend fun setLastTwelveWeeksStepData() {
        stepData.postValue(stepActivityDataSource.getLastTwelveWeeksStatistics())
    }

    override suspend fun setThirtyDaysStepData() {
        stepData.postValue(stepActivityDataSource.getLastThirtyDaysStatistics())
    }

    companion object {
        private const val DEFAULT_GOAL = 500
    }
}