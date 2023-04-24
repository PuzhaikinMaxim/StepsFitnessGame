package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.GuildEnterRequestRepositoryImpl
import com.puj.stepsfitnessgame.data.repositories.GuildRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.GuildEnterRequestRepository
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository
import com.puj.stepsfitnessgame.domain.usecases.guild.GetGuildDataUseCase
import com.puj.stepsfitnessgame.domain.usecases.guild.GetGuildListUseCase
import com.puj.stepsfitnessgame.domain.usecases.guildenterrequest.CancelEnterUseCase
import com.puj.stepsfitnessgame.domain.usecases.guildenterrequest.RequestEnterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuildListViewModel(private val sharedPreferences: SharedPreferences): ViewModel() {

    private val guildRepository: GuildRepository = GuildRepositoryImpl(sharedPreferences)

    private val guildEnterRequestRepository:
            GuildEnterRequestRepository = GuildEnterRequestRepositoryImpl(sharedPreferences)

    private val getGuildListUseCase = GetGuildListUseCase(guildRepository)

    private val requestEnterUseCase = RequestEnterUseCase(guildEnterRequestRepository)

    private val cancelEnterUseCase = CancelEnterUseCase(guildEnterRequestRepository)

    private val getGuildDataUseCase = GetGuildDataUseCase(guildRepository)

    val guildData = getGuildDataUseCase()

    val guildList = getGuildListUseCase()

    fun cancelEnterUseCase(guildId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            cancelEnterUseCase.invoke(guildId)
        }
    }

    fun requestEnterUseCase(guildId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            requestEnterUseCase.invoke(guildId)
        }
    }
}