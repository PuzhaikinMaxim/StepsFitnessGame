package com.puj.stepsfitnessgame.domain.usecases

import com.puj.stepsfitnessgame.domain.StatisticsRepository

class SetLastWeekStepDataUseCase(
    private val repository: StatisticsRepository
) {

    suspend operator fun invoke() {
        repository.setLastWeekDayData()
    }
}