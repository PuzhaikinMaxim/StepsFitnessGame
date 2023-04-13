package com.puj.stepsfitnessgame.data.network.duel

import androidx.lifecycle.LiveData
import ua.naiksoftware.stomp.StompClient

class DuelRemoteDataSourceImpl(
    private val token: String,
    private val username: String
): DuelRemoteDataSource {

    private var duelStompClient: DuelStompClient? = null

    fun startChallenge() {
        duelStompClient = DuelStompClient(token,username)
    }

    override fun startDuelSearch(): LiveData<Boolean> {
        TODO("Not yet implemented")
    }

    override fun stopDuelSearch() {
        duelStompClient?.stopDuelSearch()
    }

    override fun updateStepAmount(): Boolean {
        TODO("Not yet implemented")
    }
}