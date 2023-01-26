package com.puj.stepsfitnessgame.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase
import com.puj.stepsfitnessgame.data.network.stepactivity.StepActivityDataSource
import com.puj.stepsfitnessgame.domain.StatisticsRepository
import com.puj.stepsfitnessgame.domain.models.statistics.DayData
import com.puj.stepsfitnessgame.domain.models.statistics.StepData
import com.puj.stepsfitnessgame.domain.models.statistics.TodayStatistics

class StatisticsRepositoryImpl: StatisticsRepository {

    private val stepActivityDataSource = StepActivityDataSource()

    private val stepData = MutableLiveData<List<StepData>>()

    private val todayStatistics = MutableLiveData<TodayStatistics>()

    private val userGoalDao = FitnessGameDatabase.getDatabase().goalDao()

    private val lastWeekStepData = MutableLiveData<List<StepData>>()

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
        stepData.postValue(stepActivityDataSource.getLastTwelveMonthStatistics().reversed())
    }

    override suspend fun setLastTwelveWeeksStepData() {
        stepData.postValue(stepActivityDataSource.getLastTwelveWeeksStatistics().reversed())
    }

    override suspend fun setThirtyDaysStepData() {
        stepData.postValue(stepActivityDataSource.getLastThirtyDaysStatistics().reversed())
    }

    override fun getLastWeekDayData(): LiveData<List<DayData>> {
        return Transformations.map(lastWeekStepData) {
            val newList = ArrayList<DayData>()
            val goal = userGoalDao.getGoal()?.goal ?: DEFAULT_GOAL

            for (elem in it){
                newList.add(
                    DayData(
                        elem.stepAmount,
                        goal,
                        elem.date,
                        DayData.getWeekRepresentation(elem.date.dayOfWeek.value)
                    )
                )
            }
            newList
        }
    }

    override suspend fun setLastWeekDayData() {
        lastWeekStepData.postValue(stepActivityDataSource.getLastSevenDaysStatistics().reversed())
    }

    companion object {
        private const val DEFAULT_GOAL = 500
    }
}