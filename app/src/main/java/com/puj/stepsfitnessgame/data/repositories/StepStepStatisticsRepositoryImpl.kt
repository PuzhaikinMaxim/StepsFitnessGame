package com.puj.stepsfitnessgame.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase
import com.puj.stepsfitnessgame.data.database.usergoal.UserGoalDbModel
import com.puj.stepsfitnessgame.data.stepactivity.StepActivityDataSource
import com.puj.stepsfitnessgame.domain.repositories.StepStatisticsRepository
import com.puj.stepsfitnessgame.domain.models.stepstatistics.DayData
import com.puj.stepsfitnessgame.domain.models.stepstatistics.Goal
import com.puj.stepsfitnessgame.domain.models.stepstatistics.StepData
import com.puj.stepsfitnessgame.domain.models.stepstatistics.TodayStatistics

class StepStepStatisticsRepositoryImpl: StepStatisticsRepository {

    private val stepActivityDataSource = StepActivityDataSource

    private val stepData = MutableLiveData<List<StepData>>()

    private val todayStatistics = MutableLiveData<TodayStatistics>()

    private val userGoalDao = FitnessGameDatabase.getDatabase().goalDao()

    private val lastWeekStepData = MutableLiveData<List<StepData>>()

    private val selectedGoals = MutableLiveData<List<Goal>>()

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

    override fun getGoalsList(): LiveData<List<Goal>> {
        return selectedGoals
    }

    override suspend fun setGoalsList() {
        val selectedGoal = getSelectedGoal()

        val goalsList = ArrayList<Goal>()

        for(steps in MIN_STEP_COUNT..MAX_STEP_COUNT step 500){
            if(selectedGoal.amountOfSteps == steps){
                goalsList.add(selectedGoal)
            }
            else{
                goalsList.add(
                    Goal(steps, getEstimatedKilometersInString(steps), false)
                )
            }
        }
        selectedGoals.postValue(goalsList)
    }

    private fun getSelectedGoal(): Goal {
        val selectedGoal = userGoalDao.getGoal()
        val stepsForGoal = selectedGoal?.goal ?: DEFAULT_GOAL
        return Goal(
                stepsForGoal,
                getEstimatedKilometersInString(stepsForGoal),
                true
            )
    }

    private fun getEstimatedKilometersInString(stepAmount: Int): String{
        return String.format ("%.1f",stepAmount.toDouble()/1400)
    }

    override suspend fun setGoal(goalValue: Int) {
        userGoalDao.setNewGoal(UserGoalDbModel(goal = goalValue))
    }

    companion object {
        private const val DEFAULT_GOAL = 500
        private const val MIN_STEP_COUNT = DEFAULT_GOAL
        private const val MAX_STEP_COUNT = 100000
    }
}