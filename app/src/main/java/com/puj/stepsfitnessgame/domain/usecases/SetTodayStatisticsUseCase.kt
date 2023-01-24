package com.puj.stepsfitnessgame.domain.usecases

import com.puj.stepsfitnessgame.domain.StatisticsRepository

class SetTodayStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) {

    suspend operator fun invoke() {
        statisticsRepository.setTodayStatistics()
    }
}