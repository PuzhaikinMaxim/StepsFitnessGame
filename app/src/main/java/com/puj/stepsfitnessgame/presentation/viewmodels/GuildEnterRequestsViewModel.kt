package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.GuildEnterRequestRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.GuildEnterRequestRepository
import com.puj.stepsfitnessgame.domain.usecases.guildenterrequest.AcceptEnterUseCase
import com.puj.stepsfitnessgame.domain.usecases.guildenterrequest.GetGuildEnterRequestsUseCase
import com.puj.stepsfitnessgame.domain.usecases.guildenterrequest.RefuseEnterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuildEnterRequestsViewModel(private val sharedPreferences: SharedPreferences): ViewModel() {

    private val guildEnterRequestRepository:
            GuildEnterRequestRepository = GuildEnterRequestRepositoryImpl(sharedPreferences)

    private val getGuildEnterRequestUseCase = GetGuildEnterRequestsUseCase(guildEnterRequestRepository)

    private val acceptEnterUseCase = AcceptEnterUseCase(guildEnterRequestRepository)

    private val refuseEnterUseCase = RefuseEnterUseCase(guildEnterRequestRepository)

    val guildEnterRequestList = getGuildEnterRequestUseCase()

    fun acceptEnter(requestId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            acceptEnterUseCase.invoke(requestId)
            getGuildEnterRequestUseCase()
        }
    }

    fun refuseEnter(requestId: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            refuseEnterUseCase.invoke(requestId)
            getGuildEnterRequestUseCase()
        }
    }
}