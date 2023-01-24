package com.puj.stepsfitnessgame.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.StatisticsRepositoryImpl
import com.puj.stepsfitnessgame.domain.StatisticsRepository
import com.puj.stepsfitnessgame.domain.models.statistics.StepData
import com.puj.stepsfitnessgame.domain.models.statistics.TodayStatistics
import com.puj.stepsfitnessgame.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticsViewModel: ViewModel() {

    private val statisticsRepository: StatisticsRepository = StatisticsRepositoryImpl()

    private val setLastThirtyDaysStepDataUseCase =
        SetLastThirtyDaysStepDataUseCase(statisticsRepository)

    private val setLastTwelveWeeksStepDataUseCase =
        SetLastTwelveWeeksStepDataUseCase(statisticsRepository)

    private val setLastTwelveMonthsStepDataUseCase =
        SetLastTwelveMonthsStepDataUseCase(statisticsRepository)

    private val getTodayStatisticsUseCase = GetTodayStatisticsUseCase(statisticsRepository)

    private val setTodayStatisticsUseCase = SetTodayStatisticsUseCase(statisticsRepository)

    private val getLastDatesStepData = GetLastDatesStepData(statisticsRepository)

    private val _stepData = getLastDatesStepData.invoke()
    val stepData: LiveData<List<StepData>>
        get() = _stepData

    private var _todayStatistics = getTodayStatisticsUseCase.invoke()
    val todayStatistics: LiveData<TodayStatistics>
        get() = _todayStatistics

    init {
        viewModelScope.launch(Dispatchers.Default) {
            setTodayStatisticsUseCase()
            setLastThirtyDaysStepDataUseCase()
        }
    }

    fun setLastThirtyDaysStepData() {
        viewModelScope.launch(Dispatchers.Default) {
            setLastThirtyDaysStepDataUseCase()
        }
    }

    fun setLastTwelveWeeksStepData() {
        viewModelScope.launch(Dispatchers.Default) {
            setLastTwelveWeeksStepDataUseCase()
        }
    }

    fun setLastTwelveMonthsStepData() {
        viewModelScope.launch(Dispatchers.Default) {
            setLastTwelveMonthsStepDataUseCase()
        }
    }
}