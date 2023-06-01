package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.DuelRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.duel.FinishedDuelReward
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository
import com.puj.stepsfitnessgame.domain.usecases.duel.CancelDuelUseCase
import com.puj.stepsfitnessgame.domain.usecases.duel.ClaimRewardUseCase
import com.puj.stepsfitnessgame.domain.usecases.duel.GetDuelFieldUseCase
import kotlinx.coroutines.*

class DuelFieldViewModel(sharedPreferences: SharedPreferences) : ViewModel() {

    private val repository: DuelRepository = DuelRepositoryImpl(sharedPreferences)

    private val getDuelFieldUseCase = GetDuelFieldUseCase(repository)

    private val claimRewardUseCase = ClaimRewardUseCase(repository)

    private val cancelDuelUseCase = CancelDuelUseCase(repository)

    private val _finishedDuelReward = MutableLiveData<FinishedDuelReward>()
    val finishedDuelReward: LiveData<FinishedDuelReward>
        get() = _finishedDuelReward

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private lateinit var duelFieldUpdateCoroutine: Job

    val duelField = getDuelFieldUseCase()

    fun cancelDuel() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = cancelDuelUseCase.invoke()
            if(result){
                getDuelFieldUseCase()
            }
        }
    }

    fun claimReward() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = claimRewardUseCase.invoke()
            if(result != null){
                _finishedDuelReward.postValue(result)
            }
        }
    }

    fun startPeriodicalDataUpdate() {
        if(this::duelFieldUpdateCoroutine.isInitialized && !duelFieldUpdateCoroutine.isActive){
            duelFieldUpdateCoroutine.start()
        }
        duelFieldUpdateCoroutine = viewModelScope.launch(Dispatchers.Main) {
            while (true) {
                delay(ONE_MINUTE)
                ensureActive()
                getDuelFieldUseCase()
            }
        }
    }

    fun stopPeriodicalDataUpdate() {
        duelFieldUpdateCoroutine.cancel()
    }

    fun closeScreen() {
        _shouldCloseScreen.value = Unit
    }

    companion object {

        private const val ONE_MINUTE = 1000L * 60
    }
}