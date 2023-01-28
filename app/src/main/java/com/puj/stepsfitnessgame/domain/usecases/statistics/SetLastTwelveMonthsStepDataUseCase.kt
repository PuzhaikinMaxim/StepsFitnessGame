package com.puj.stepsfitnessgame.domain.usecases.statistics

import com.puj.stepsfitnessgame.domain.StatisticsRepository

class SetLastTwelveMonthsStepDataUseCase(
    private val statisticsRepository: StatisticsRepository
) {

    suspend operator fun invoke() {
        statisticsRepository.setLastTwelveMonthStepData()
    }
}