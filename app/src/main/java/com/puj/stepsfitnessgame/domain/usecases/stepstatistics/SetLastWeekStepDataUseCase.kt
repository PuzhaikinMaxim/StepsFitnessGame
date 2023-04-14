package com.puj.stepsfitnessgame.domain.usecases.stepstatistics

import com.puj.stepsfitnessgame.domain.repositories.StepStatisticsRepository

class SetLastWeekStepDataUseCase(
    private val repository: StepStatisticsRepository
) {

    suspend operator fun invoke() {
        repository.setLastWeekDayData()
    }
}