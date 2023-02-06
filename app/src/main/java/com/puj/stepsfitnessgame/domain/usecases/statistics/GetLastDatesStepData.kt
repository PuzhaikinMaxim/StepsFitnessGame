package com.puj.stepsfitnessgame.domain.usecases.statistics

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.repositories.StatisticsRepository
import com.puj.stepsfitnessgame.domain.models.statistics.StepData

class GetLastDatesStepData(
    private val repository: StatisticsRepository
) {

    operator fun invoke(): LiveData<List<StepData>> {
        return repository.getLastDatesStepData()
    }
}