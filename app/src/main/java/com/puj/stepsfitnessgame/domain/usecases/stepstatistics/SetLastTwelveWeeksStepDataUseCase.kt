package com.puj.stepsfitnessgame.domain.usecases.stepstatistics

import com.puj.stepsfitnessgame.domain.repositories.StepStatisticsRepository

class SetLastTwelveWeeksStepDataUseCase(
    private val stepStatisticsRepository: StepStatisticsRepository
) {

    suspend operator fun invoke() {
        stepStatisticsRepository.setLastTwelveWeeksStepData()
    }
}