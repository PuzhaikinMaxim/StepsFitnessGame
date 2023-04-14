package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.DuelRepositoryImpl
import com.puj.stepsfitnessgame.data.repositories.PlayerStatisticsRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository
import com.puj.stepsfitnessgame.domain.repositories.PlayerStatisticsRepository
import com.puj.stepsfitnessgame.domain.usecases.playerstatistics.GetDuelStatisticsUseCase

class DuelStatisticsViewModel(sharedPreferences: SharedPreferences) : ViewModel() {

    private val repository: PlayerStatisticsRepository = PlayerStatisticsRepositoryImpl(
        sharedPreferences
    )

    private val getDuelStatisticsUseCase = GetDuelStatisticsUseCase(repository)

    val duelStatistics = getDuelStatisticsUseCase.invoke()
}