package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.DuelRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository

class DuelFieldViewModel(sharedPreferences: SharedPreferences) : ViewModel() {

    private val repository: DuelRepository = DuelRepositoryImpl(sharedPreferences)
}