package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.LevelRepositoryImpl
import com.puj.stepsfitnessgame.domain.usecases.level.GetLevelListUseCase
import com.puj.stepsfitnessgame.domain.usecases.level.SelectLevelUseCase

class SelectLevelViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val repository = LevelRepositoryImpl(sharedPreferences)

    private val getLevelListUseCase = GetLevelListUseCase(repository)

    private val selectLevelUseCase = SelectLevelUseCase(repository)

    val levelList = getLevelListUseCase.invoke()

    fun selectLevel(level: Int) {
        selectLevelUseCase.invoke(level)
    }
}