package com.puj.stepsfitnessgame.domain

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.statistics.TodayStatistics
import com.puj.stepsfitnessgame.domain.models.statistics.StepData

interface StatisticsRepository {

    fun getTodayStatistics(): LiveData<TodayStatistics>

    suspend fun setTodayStatistics()

    fun getLastDatesStepData(): LiveData<List<StepData>>

    suspend fun setLastTwelveMonthStepData()

    suspend fun setLastTwelveWeeksStepData()

    suspend fun setThirtyDaysStepData()
}