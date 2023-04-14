package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.stepstatistics.DayData
import com.puj.stepsfitnessgame.domain.models.stepstatistics.Goal
import com.puj.stepsfitnessgame.domain.models.stepstatistics.TodayStatistics
import com.puj.stepsfitnessgame.domain.models.stepstatistics.StepData

interface StepStatisticsRepository {

    fun getTodayStatistics(): LiveData<TodayStatistics>

    suspend fun setTodayStatistics()

    fun getLastDatesStepData(): LiveData<List<StepData>>

    suspend fun setLastTwelveMonthStepData()

    suspend fun setLastTwelveWeeksStepData()

    suspend fun setThirtyDaysStepData()

    fun getLastWeekDayData(): LiveData<List<DayData>>

    suspend fun setLastWeekDayData()

    fun getGoalsList(): LiveData<List<Goal>>

    suspend fun setGoalsList()

    suspend fun setGoal(goalValue: Int)
}