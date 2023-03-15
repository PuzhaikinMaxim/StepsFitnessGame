package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.statistics.DayData
import com.puj.stepsfitnessgame.domain.models.statistics.Goal
import com.puj.stepsfitnessgame.domain.models.statistics.TodayStatistics
import com.puj.stepsfitnessgame.domain.models.statistics.StepData

interface StatisticsRepository {

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