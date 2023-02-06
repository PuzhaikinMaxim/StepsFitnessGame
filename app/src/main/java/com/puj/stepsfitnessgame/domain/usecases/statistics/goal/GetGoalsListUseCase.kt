package com.puj.stepsfitnessgame.domain.usecases.statistics.goal

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.repositories.StatisticsRepository
import com.puj.stepsfitnessgame.domain.models.statistics.Goal

class GetGoalsListUseCase(
    private val repository: StatisticsRepository
) {

    operator fun invoke(): LiveData<List<Goal>> {
        return repository.getGoalsList()
    }
}