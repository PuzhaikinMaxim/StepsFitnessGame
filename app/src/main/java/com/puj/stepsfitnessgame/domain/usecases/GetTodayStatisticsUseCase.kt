package com.puj.stepsfitnessgame.domain.usecases

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.StatisticsRepository
import com.puj.stepsfitnessgame.domain.models.statistics.TodayStatistics

class GetTodayStatisticsUseCase(
    private val repository: StatisticsRepository
) {

    operator fun invoke(): LiveData<TodayStatistics> {
        return repository.getTodayStatistics()
    }
}