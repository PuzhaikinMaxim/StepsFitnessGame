package com.puj.stepsfitnessgame.domain.usecases.achievement

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.achievement.Achievement
import com.puj.stepsfitnessgame.domain.repositories.AchievementRepository

class GetAchievementListUseCase(private val repository: AchievementRepository) {

    operator fun invoke(): LiveData<List<Achievement>> {
        return repository.getAchievementList()
    }
}