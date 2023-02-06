package com.puj.stepsfitnessgame.domain.usecases.statistics.goal

import com.puj.stepsfitnessgame.domain.repositories.StatisticsRepository

class SetGoalUseCase(
    private val repository: StatisticsRepository
) {

    suspend operator fun invoke(newGoal: Int) {
        repository.setGoal(newGoal)
    }
}