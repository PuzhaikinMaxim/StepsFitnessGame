package com.puj.stepsfitnessgame.domain.usecases.statistics.goal

import com.puj.stepsfitnessgame.domain.repositories.StatisticsRepository

class SetGoalsListUseCase(
    private val repository: StatisticsRepository
) {

    suspend operator fun invoke() {
        repository.setGoalsList()
    }
}