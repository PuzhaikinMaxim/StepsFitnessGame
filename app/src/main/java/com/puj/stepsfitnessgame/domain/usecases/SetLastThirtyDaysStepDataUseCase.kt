package com.puj.stepsfitnessgame.domain.usecases

import com.puj.stepsfitnessgame.domain.StatisticsRepository

class SetLastThirtyDaysStepDataUseCase(
    private val statisticsRepository: StatisticsRepository
) {

    suspend operator fun invoke() {
        statisticsRepository.setThirtyDaysStepData()
    }
}