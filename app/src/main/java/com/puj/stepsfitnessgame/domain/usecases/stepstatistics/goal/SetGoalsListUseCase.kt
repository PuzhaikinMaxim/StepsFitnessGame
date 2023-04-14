package com.puj.stepsfitnessgame.domain.usecases.stepstatistics.goal

import com.puj.stepsfitnessgame.domain.repositories.StepStatisticsRepository

class SetGoalsListUseCase(
    private val repository: StepStatisticsRepository
) {

    suspend operator fun invoke() {
        repository.setGoalsList()
    }
}