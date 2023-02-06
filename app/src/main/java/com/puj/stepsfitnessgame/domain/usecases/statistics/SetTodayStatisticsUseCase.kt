package com.puj.stepsfitnessgame.domain.usecases.statistics

import com.puj.stepsfitnessgame.domain.repositories.StatisticsRepository

class SetTodayStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) {

    suspend operator fun invoke() {
        statisticsRepository.setTodayStatistics()
    }
}