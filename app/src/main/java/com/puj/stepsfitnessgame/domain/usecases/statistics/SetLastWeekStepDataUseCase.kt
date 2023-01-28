package com.puj.stepsfitnessgame.domain.usecases.statistics

import com.puj.stepsfitnessgame.domain.StatisticsRepository

class SetLastWeekStepDataUseCase(
    private val repository: StatisticsRepository
) {

    suspend operator fun invoke() {
        repository.setLastWeekDayData()
    }
}