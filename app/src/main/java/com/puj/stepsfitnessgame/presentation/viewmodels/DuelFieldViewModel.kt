package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.DuelRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository
import com.puj.stepsfitnessgame.domain.usecases.duel.GetDuelFieldUseCase

class DuelFieldViewModel(sharedPreferences: SharedPreferences) : ViewModel() {

    private val repository: DuelRepository = DuelRepositoryImpl(sharedPreferences)

    private val getDuelFieldUseCase = GetDuelFieldUseCase(repository)

    val duelField = getDuelFieldUseCase()
}