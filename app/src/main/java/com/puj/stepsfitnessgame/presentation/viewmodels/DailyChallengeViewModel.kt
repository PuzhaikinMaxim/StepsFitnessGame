package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.DailyChallengesRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.dailychallenge.CompletedDailyChallengeReward
import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge
import com.puj.stepsfitnessgame.domain.repositories.DailyChallengesRepository
import com.puj.stepsfitnessgame.domain.usecases.dailychallenge.ClaimDailyChallengeRewardUseCase
import com.puj.stepsfitnessgame.domain.usecases.dailychallenge.GetDailyChallengesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyChallengeViewModel(
    sharedPreferences: SharedPreferences
): ViewModel() {

    private val repository: DailyChallengesRepository = DailyChallengesRepositoryImpl(sharedPreferences)

    private val getDailyChallengesUseCase = GetDailyChallengesUseCase(repository)

    private val claimDailyChallengeRewardUseCase = ClaimDailyChallengeRewardUseCase(repository)

    private val _dailyTasksList = getDailyChallengesUseCase()
    val dailyTasksList: LiveData<List<DailyChallenge>>
        get() = _dailyTasksList

    private var _completedDailyChallengeReward = MutableLiveData<CompletedDailyChallengeReward>()
    val completedDailyChallengeReward: LiveData<CompletedDailyChallengeReward>
        get() = _completedDailyChallengeReward

    private var _shouldShowRewardModal = MutableLiveData(false)
    val shouldShowRewardModal: LiveData<Boolean>
        get() = _shouldShowRewardModal

    val amountOfCompletedTasks: Int
        get() = _dailyTasksList.value?.count { it.isCompleted } ?: 0

    val amountOfTasks: Int
        get() = _dailyTasksList.value?.size ?: 0

    fun claimDailyChallengeReward() {
        viewModelScope.launch(Dispatchers.Default) {
            val reward = claimDailyChallengeRewardUseCase()
            if(reward != null){
                setupModalData(reward)
            }
        }
    }

    fun updateDailyTasksList() {
        getDailyChallengesUseCase()
    }

    fun closeModal() {
        _shouldShowRewardModal.postValue(false)
    }

    private suspend fun setupModalData(reward: CompletedDailyChallengeReward) {
        _completedDailyChallengeReward.postValue(reward)
        _shouldShowRewardModal.postValue(true)
    }
}