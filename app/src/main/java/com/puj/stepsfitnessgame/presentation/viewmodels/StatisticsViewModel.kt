package com.puj.stepsfitnessgame.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.StepStatisticsRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.StepStatisticsRepository
import com.puj.stepsfitnessgame.domain.models.stepstatistics.DayData
import com.puj.stepsfitnessgame.domain.models.stepstatistics.StepData
import com.puj.stepsfitnessgame.domain.models.stepstatistics.TodayStatistics
import com.puj.stepsfitnessgame.domain.usecases.stepstatistics.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticsViewModel: ViewModel() {

    private val stepStatisticsRepository: StepStatisticsRepository = StepStatisticsRepositoryImpl()

    private val setLastThirtyDaysStepDataUseCase =
        SetLastThirtyDaysStepDataUseCase(stepStatisticsRepository)

    private val setLastTwelveWeeksStepDataUseCase =
        SetLastTwelveWeeksStepDataUseCase(stepStatisticsRepository)

    private val setLastTwelveMonthsStepDataUseCase =
        SetLastTwelveMonthsStepDataUseCase(stepStatisticsRepository)

    private val getTodayStatisticsUseCase = GetTodayStatisticsUseCase(stepStatisticsRepository)

    private val setTodayStatisticsUseCase = SetTodayStatisticsUseCase(stepStatisticsRepository)

    private val getLastDatesStepData = GetLastDatesStepData(stepStatisticsRepository)

    private val getLastWeekStepDataUseCase = GetLastWeekStepDataUseCase(stepStatisticsRepository)

    private val setLastWeekStepDataUseCase = SetLastWeekStepDataUseCase(stepStatisticsRepository)

    private val _stepData = getLastDatesStepData()
    val stepData: LiveData<List<StepData>>
        get() = _stepData

    private var _todayStatistics = getTodayStatisticsUseCase()
    val todayStatistics: LiveData<TodayStatistics>
        get() = _todayStatistics

    private var _weekStepData = getLastWeekStepDataUseCase()
    val weekStatistics: LiveData<List<DayData>>
        get() = _weekStepData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            setTodayStatisticsUseCase()
            setLastThirtyDaysStepDataUseCase()
            setLastWeekStepDataUseCase()
        }
    }

    fun setLastThirtyDaysStepData() {
        viewModelScope.launch(Dispatchers.IO) {
            setLastThirtyDaysStepDataUseCase()
        }
    }

    fun setLastTwelveWeeksStepData() {
        viewModelScope.launch(Dispatchers.IO) {
            setLastTwelveWeeksStepDataUseCase()
        }
    }

    fun setLastTwelveMonthsStepData() {
        viewModelScope.launch(Dispatchers.IO) {
            setLastTwelveMonthsStepDataUseCase()
        }
    }

    fun resetStatistics() {
        viewModelScope.launch(Dispatchers.IO) {
            setTodayStatisticsUseCase()
            setLastWeekStepDataUseCase()
        }
    }
}