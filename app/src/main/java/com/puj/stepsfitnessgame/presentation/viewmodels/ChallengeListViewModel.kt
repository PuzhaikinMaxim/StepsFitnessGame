package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.ChallengeRepositoryImpl
import com.puj.stepsfitnessgame.data.repositories.StatisticsRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import com.puj.stepsfitnessgame.domain.models.statistics.TodayStatistics
import com.puj.stepsfitnessgame.domain.usecases.challenge.CancelActiveChallengeUseCase
import com.puj.stepsfitnessgame.domain.usecases.challenge.GetChallengeListUseCase
import com.puj.stepsfitnessgame.domain.usecases.challenge.StartChallengeUseCase
import com.puj.stepsfitnessgame.domain.usecases.statistics.GetTodayStatisticsUseCase
import com.puj.stepsfitnessgame.domain.usecases.statistics.SetTodayStatisticsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeListViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val statisticsRepository = StatisticsRepositoryImpl()

    private val setTodayStatisticsUseCase = SetTodayStatisticsUseCase(statisticsRepository)

    private val getTodayStatisticsUseCase = GetTodayStatisticsUseCase(statisticsRepository)

    private val challengeRepository = ChallengeRepositoryImpl(sharedPreferences)

    private val getChallengeListUseCase = GetChallengeListUseCase(challengeRepository)

    private val cancelActiveChallengeUseCase = CancelActiveChallengeUseCase(challengeRepository)

    private val startChallengeUseCase = StartChallengeUseCase(challengeRepository)

    private var _challengeList = getChallengeListUseCase.invoke()
    val challengeList: LiveData<List<Challenge>>
        get() = _challengeList

    private var _todayStatistics = getTodayStatisticsUseCase.invoke()
    val todayStatistics: LiveData<TodayStatistics>
        get() = _todayStatistics

    init {
        viewModelScope.launch(Dispatchers.Default) {
            setTodayStatisticsUseCase()
        }
    }

    fun changeChallengeDetailsVisibility(challenge: Challenge) {
        val newList = _challengeList.value?.toMutableList()
        val itemPosition = newList?.indexOf(challenge)
        if(itemPosition != null){
            newList[itemPosition] = challenge.copy(isShown = !challenge.isShown)
        }
        _challengeList.value = newList
    }

    fun startChallenge(challenge: Challenge) {
        viewModelScope.launch(Dispatchers.Default) {
            startChallengeUseCase.invoke(challenge.id)
        }
    }

    fun cancelActiveChallenge() {
        viewModelScope.launch(Dispatchers.Default) {
            cancelActiveChallengeUseCase.invoke()
        }
    }
}