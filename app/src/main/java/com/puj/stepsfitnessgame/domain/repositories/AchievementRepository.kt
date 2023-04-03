package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.achievement.Achievement

interface AchievementRepository {

    fun getAchievementList(): LiveData<List<Achievement>>
}