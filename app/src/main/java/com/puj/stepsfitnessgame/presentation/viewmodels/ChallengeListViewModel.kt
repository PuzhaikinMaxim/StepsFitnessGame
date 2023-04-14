package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.ChallengeRepositoryImpl
import com.puj.stepsfitnessgame.data.repositories.StepStepStatisticsRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward
import com.puj.stepsfitnessgame.domain.models.stepstatistics.TodayStatistics
import com.puj.stepsfitnessgame.domain.usecases.challenge.CancelActiveChallengeUseCase
import com.puj.stepsfitnessgame.domain.usecases.challenge.EndActiveChallengeUseCase
import com.puj.stepsfitnessgame.domain.usecases.challenge.GetChallengeListUseCase
import com.puj.stepsfitnessgame.domain.usecases.challenge.StartChallengeUseCase
import com.puj.stepsfitnessgame.domain.usecases.stepstatistics.GetTodayStatisticsUseCase
import com.puj.stepsfitnessgame.domain.usecases.stepstatistics.SetTodayStatisticsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeListViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val statisticsRepository = StepStepStatisticsRepositoryImpl()

    private val setTodayStatisticsUseCase = SetTodayStatisticsUseCase(statisticsRepository)

    private val getTodayStatisticsUseCase = GetTodayStatisticsUseCase(statisticsRepository)

    private val challengeRepository = ChallengeRepositoryImpl(sharedPreferences)

    private val getChallengeListUseCase = GetChallengeListUseCase(challengeRepository)

    private val cancelActiveChallengeUseCase = CancelActiveChallengeUseCase(challengeRepository)

    private val startChallengeUseCase = StartChallengeUseCase(challengeRepository)

    private val endActiveChallengeUseCase = EndActiveChallengeUseCase(challengeRepository)

    private var _completedChallengeReward = MutableLiveData<CompletedChallengeReward>()
    val completedChallengeReward: LiveData<CompletedChallengeReward>
        get() = _completedChallengeReward

    private var _shouldShowRewardModal = MutableLiveData(false)
    val shouldShowRewardModal: LiveData<Boolean>
        get() = _shouldShowRewardModal

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
            getChallengeListUseCase()
        }
    }

    fun cancelActiveChallenge() {
        viewModelScope.launch(Dispatchers.Default) {
            cancelActiveChallengeUseCase.invoke()
        }
    }

    fun endActiveChallenge() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = endActiveChallengeUseCase.invoke()
            if(result != null){
                _completedChallengeReward.postValue(result)
                _shouldShowRewardModal.postValue(true)
            }
            getChallengeListUseCase()
        }
    }

    fun closeRewardModal() {
        _shouldShowRewardModal.value = false
    }
}