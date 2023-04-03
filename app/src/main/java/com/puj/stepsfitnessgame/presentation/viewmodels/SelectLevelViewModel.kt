package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.ChallengeLevelRepositoryImpl
import com.puj.stepsfitnessgame.domain.usecases.challengelevel.GetLevelListUseCase
import com.puj.stepsfitnessgame.domain.usecases.challengelevel.SelectLevelUseCase

class SelectLevelViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val repository = ChallengeLevelRepositoryImpl(sharedPreferences)

    private val getLevelListUseCase = GetLevelListUseCase(repository)

    private val selectLevelUseCase = SelectLevelUseCase(repository)

    val levelList = getLevelListUseCase.invoke()

    fun selectLevel(level: Int) {
        selectLevelUseCase.invoke(level)
    }
}