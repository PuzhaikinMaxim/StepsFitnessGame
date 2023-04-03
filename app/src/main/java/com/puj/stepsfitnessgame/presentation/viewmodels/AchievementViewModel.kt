package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.AchievementRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.achievement.Achievement
import com.puj.stepsfitnessgame.domain.repositories.AchievementRepository
import com.puj.stepsfitnessgame.domain.usecases.achievement.GetAchievementListUseCase

class AchievementViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val repository: AchievementRepository = AchievementRepositoryImpl(sharedPreferences)

    private val getAchievementListUseCase = GetAchievementListUseCase(repository)

    val achievementList = getAchievementListUseCase()

    val stepAchievementsList: List<Achievement>
        get() = achievementList.value?.filter {
            it.achievementType == Achievement.achievementTypeStep
        } ?: listOf()

    val challengeAchievementsList: List<Achievement>
        get() = achievementList.value?.filter {
            it.achievementType == Achievement.achievementTypeChallenge
        } ?: listOf()

    val duelAchievementsList: List<Achievement>
        get() = achievementList.value?.filter {
            it.achievementType == Achievement.achievementTypeDuel
        } ?: listOf()
}