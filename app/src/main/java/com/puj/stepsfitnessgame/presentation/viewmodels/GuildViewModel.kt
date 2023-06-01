package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.GuildChallengeRepositoryImpl
import com.puj.stepsfitnessgame.data.repositories.GuildRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward
import com.puj.stepsfitnessgame.domain.repositories.GuildChallengeRepository
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository
import com.puj.stepsfitnessgame.domain.usecases.guild.*
import com.puj.stepsfitnessgame.domain.usecases.guildchallenge.GetCurrentGuildChallengeUseCase
import kotlinx.coroutines.*

class GuildViewModel(private val sharedPreferences: SharedPreferences): ViewModel() {

    private val guildRepository: GuildRepository = GuildRepositoryImpl(sharedPreferences)

    private val guildChallengeRepository:
            GuildChallengeRepository = GuildChallengeRepositoryImpl(sharedPreferences)

    private val getGuildDataUseCase = GetGuildDataUseCase(guildRepository)

    private val claimRewardUseCase = ClaimRewardUseCase(guildRepository)

    private val expelGuildParticipantUseCase = ExpelGuildParticipantUseCase(guildRepository)

    private val getGuildParticipantsUseCase = GetGuildParticipantsUseCase(guildRepository)

    private val getGuildStatisticsUseCase = GetGuildStatisticsUseCase(guildRepository)

    private val getIsOwnerUseCase = GetIsOwnerUseCase(guildRepository)

    private val getHasRewardUseCase = GetHasRewardUseCase(guildRepository)

    private val getCurrentGuildChallengeUseCase = GetCurrentGuildChallengeUseCase(guildChallengeRepository)

    private val _challengeReward = MutableLiveData<CompletedChallengeReward>()
    val challengeReward: LiveData<CompletedChallengeReward>
        get() = _challengeReward

    private val _shouldOpenRewardModal = MutableLiveData<Boolean>()
    val shouldOpenRewardModal: LiveData<Boolean>
        get() = _shouldOpenRewardModal

    private val _hasReward = MutableLiveData<Boolean>()
    val hasReward: LiveData<Boolean>
        get() = _hasReward

    val guildParticipants = getGuildParticipantsUseCase()

    val currentChallenge = getCurrentGuildChallengeUseCase()

    val guildStatistics = getGuildStatisticsUseCase()

    val guildData = getGuildDataUseCase()

    private lateinit var guildDataUpdateCoroutine: Job

    private val _isOwner = MutableLiveData<Boolean>()
    val isOwner: LiveData<Boolean>
        get() = _isOwner

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isOwner.postValue(getIsOwnerUseCase())
            _hasReward.postValue(getHasRewardUseCase())
        }
    }

    fun expelGuildParticipant(participantId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            expelGuildParticipantUseCase.invoke(participantId)
            getGuildParticipantsUseCase.invoke()
        }
    }

    fun claimReward() {
        viewModelScope.launch(Dispatchers.IO) {
            val challengeReward = claimRewardUseCase() ?: return@launch
            _challengeReward.postValue(challengeReward)
            _shouldOpenRewardModal.postValue(true)
            _hasReward.postValue(false)
        }
    }

    fun openRewardModal() {
        _shouldOpenRewardModal.value = true
    }

    fun closeRewardModal() {
        _shouldOpenRewardModal.value = false
    }

    fun startPeriodicalDataUpdate() {
        if(this::guildDataUpdateCoroutine.isInitialized && !guildDataUpdateCoroutine.isActive){
            guildDataUpdateCoroutine.start()
        }
        guildDataUpdateCoroutine = viewModelScope.launch(Dispatchers.Main) {
            while (true) {
                delay(THREE_MINUTES)
                ensureActive()
                getGuildParticipantsUseCase()
                getCurrentGuildChallengeUseCase()
                getGuildStatisticsUseCase()
            }
        }
    }

    fun stopPeriodicalDataUpdate() {
        guildDataUpdateCoroutine.cancel()
    }

    fun resetData() {
        getGuildDataUseCase()
        getCurrentGuildChallengeUseCase()
        getGuildParticipantsUseCase()
        getGuildStatisticsUseCase()
    }

    companion object {
        private const val THREE_MINUTES = 1000L * 60 * 3
    }
}