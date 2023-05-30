package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.DuelRepositoryImpl
import com.puj.stepsfitnessgame.data.repositories.PlayerStatisticsRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository
import com.puj.stepsfitnessgame.domain.repositories.PlayerStatisticsRepository
import com.puj.stepsfitnessgame.domain.usecases.duel.GetIsDuelNotFinishedUseCase
import com.puj.stepsfitnessgame.domain.usecases.playerstatistics.GetDuelStatisticsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DuelStatisticsViewModel(sharedPreferences: SharedPreferences) : ViewModel() {

    private val repository: PlayerStatisticsRepository = PlayerStatisticsRepositoryImpl(
        sharedPreferences
    )

    private val duelRepository = DuelRepositoryImpl(sharedPreferences)

    private val getDuelStatisticsUseCase = GetDuelStatisticsUseCase(repository)

    private val getIsDuelNotFinishedUseCase = GetIsDuelNotFinishedUseCase(duelRepository)

    private val _isDuelNotFinished = MutableLiveData<Unit>()
    val isDuelNotFinished: LiveData<Unit>
        get() = _isDuelNotFinished

    val duelStatistics = getDuelStatisticsUseCase.invoke()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val isDuelNotFinished = getIsDuelNotFinishedUseCase()
            if(isDuelNotFinished){
                _isDuelNotFinished.postValue(Unit)
            }
        }
    }
}