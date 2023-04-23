package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.GuildChallengeRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.GuildChallengeRepository
import com.puj.stepsfitnessgame.domain.usecases.guildchallenge.GetGuildChallengesUseCase
import com.puj.stepsfitnessgame.domain.usecases.guildchallenge.SelectGuildChallengeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseGuildChallengeViewModel(private val sharedPreferences: SharedPreferences): ViewModel() {

    private val guildChallengeRepository:
            GuildChallengeRepository = GuildChallengeRepositoryImpl(sharedPreferences)

    private val getGuildChallengesUseCase = GetGuildChallengesUseCase(guildChallengeRepository)

    private val selectGuildChallengeUseCase = SelectGuildChallengeUseCase(guildChallengeRepository)

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    val guildChallenges = getGuildChallengesUseCase()

    fun selectGuildChallenge(guildChallengeId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            selectGuildChallengeUseCase.invoke(guildChallengeId)
            _shouldCloseScreen.postValue(Unit)
        }
    }
}