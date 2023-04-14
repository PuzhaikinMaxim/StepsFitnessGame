package com.puj.stepsfitnessgame.domain.usecases.stepstatistics.goal

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.repositories.StepStatisticsRepository
import com.puj.stepsfitnessgame.domain.models.stepstatistics.Goal

class GetGoalsListUseCase(
    private val repository: StepStatisticsRepository
) {

    operator fun invoke(): LiveData<List<Goal>> {
        return repository.getGoalsList()
    }
}