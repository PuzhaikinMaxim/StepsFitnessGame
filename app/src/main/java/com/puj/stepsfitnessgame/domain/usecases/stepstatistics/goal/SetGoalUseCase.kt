package com.puj.stepsfitnessgame.domain.usecases.stepstatistics.goal

import com.puj.stepsfitnessgame.domain.repositories.StepStatisticsRepository

class SetGoalUseCase(
    private val repository: StepStatisticsRepository
) {

    suspend operator fun invoke(newGoal: Int) {
        repository.setGoal(newGoal)
    }
}