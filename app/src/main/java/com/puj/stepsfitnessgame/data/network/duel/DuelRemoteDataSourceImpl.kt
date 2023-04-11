package com.puj.stepsfitnessgame.data.network.duel

import ua.naiksoftware.stomp.StompClient

class DuelRemoteDataSourceImpl: DuelRemoteDataSource {

    private var duelStompClient: DuelStompClient? = null

    fun startChallenge() {
        duelStompClient = DuelStompClient()
    }
}