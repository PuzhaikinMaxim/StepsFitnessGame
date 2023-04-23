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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuildViewModel(private val sharedPreferences: SharedPreferences): ViewModel() {

    private val guildRepository: GuildRepository = GuildRepositoryImpl(sharedPreferences)

    private val guildChallengeRepository:
            GuildChallengeRepository = GuildChallengeRepositoryImpl(sharedPreferences)

    private val getGuildDataUseCase = GetGuildListUseCase(guildRepository)

    private val claimRewardUseCase = ClaimRewardUseCase(guildRepository)

    private val expelGuildParticipantUseCase = ExpelGuildParticipantUseCase(guildRepository)

    private val getGuildParticipantsUseCase = GetGuildParticipantsUseCase(guildRepository)

    private val getGuildStatisticsUseCase = GetGuildStatisticsUseCase(guildRepository)

    private val getIsOwnerUseCase = GetIsOwnerUseCase(guildRepository)

    private val getCurrentGuildChallengeUseCase = GetCurrentGuildChallengeUseCase(guildChallengeRepository)

    private val _challengeReward = MutableLiveData<CompletedChallengeReward>()
    val challengeReward: LiveData<CompletedChallengeReward>
        get() = _challengeReward

    private val _shouldOpenRewardModal = MutableLiveData<Boolean>()
    val shouldOpenRewardModal: LiveData<Boolean>
        get() = _shouldOpenRewardModal

    val guildParticipants = getGuildParticipantsUseCase()

    val currentChallenge = getCurrentGuildChallengeUseCase()

    val guildStatistics = getGuildStatisticsUseCase()

    val guildData = getGuildDataUseCase()

    private val _isOwner = MutableLiveData(false)
    val isOwner: LiveData<Boolean>
        get() = _isOwner

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _isOwner.postValue(getIsOwnerUseCase())
        }
    }

    fun expelGuildParticipant(participantId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            expelGuildParticipantUseCase.invoke(participantId)
            getGuildParticipantsUseCase.invoke()
        }
    }

    fun claimReward() {
        viewModelScope.launch(Dispatchers.Default) {
            val challengeReward = claimRewardUseCase() ?: return@launch
            _challengeReward.postValue(challengeReward)
            _shouldOpenRewardModal.postValue(true)
        }
    }

    fun closeRewardModal() {
        _shouldOpenRewardModal.value = false
    }
}