package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.DuelRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository
import com.puj.stepsfitnessgame.domain.usecases.duel.GetIsOpponentFoundUseCase
import com.puj.stepsfitnessgame.domain.usecases.duel.StartDuelSearchUseCase
import com.puj.stepsfitnessgame.domain.usecases.duel.StopDuelSearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DuelSearchViewModel(sharedPreferences: SharedPreferences) : ViewModel() {

    private val repository: DuelRepository = DuelRepositoryImpl(sharedPreferences)

    private val startDuelSearchUseCase = StartDuelSearchUseCase(repository)

    private val stopDuelSearchUseCase = StopDuelSearchUseCase(repository)

    private val getIsOpponentFoundUseCase = GetIsOpponentFoundUseCase(repository)

    val isOpponentFound = getIsOpponentFoundUseCase()

    private var job: Job? = null

    private val _timer = MutableLiveData(0)

    val timer: LiveData<Int>
        get() = _timer

    init {
        startDuelSearchUseCase()
    }

    fun startTimer() {
        job = viewModelScope.launch(Dispatchers.Main){
            for(i in 0..360){
                _timer.postValue(i)
                delay(1000)
            }
            stopSearch()
        }
    }

    fun stopSearch() {
        job?.cancel()
        stopDuelSearchUseCase()
    }
}