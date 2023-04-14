package com.puj.stepsfitnessgame.domain.usecases.stepstatistics

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.repositories.StepStatisticsRepository
import com.puj.stepsfitnessgame.domain.models.stepstatistics.TodayStatistics

class GetTodayStatisticsUseCase(
    private val repository: StepStatisticsRepository
) {

    operator fun invoke(): LiveData<TodayStatistics> {
        return repository.getTodayStatistics()
    }
}