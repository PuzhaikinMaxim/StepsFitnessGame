package com.puj.stepsfitnessgame.domain.usecases.dailychallenge

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.dailytask.DailyTask
import com.puj.stepsfitnessgame.domain.repositories.DailyTasksRepository

class GetDailyTasksUseCase(
    private val dailyTasksRepository: DailyTasksRepository
) {

    operator fun invoke(): LiveData<List<DailyTask>> {
        return dailyTasksRepository.getDailyChallengesList()
    }
}