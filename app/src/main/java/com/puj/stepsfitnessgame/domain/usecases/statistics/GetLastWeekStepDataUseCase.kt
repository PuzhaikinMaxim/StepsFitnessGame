package com.puj.stepsfitnessgame.domain.usecases.statistics

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.StatisticsRepository
import com.puj.stepsfitnessgame.domain.models.statistics.DayData
import com.puj.stepsfitnessgame.domain.models.statistics.StepData

class GetLastWeekStepDataUseCase(
    private val repository: StatisticsRepository
) {

    operator fun invoke(): LiveData<List<DayData>> {
        return repository.getLastWeekDayData()
    }
}