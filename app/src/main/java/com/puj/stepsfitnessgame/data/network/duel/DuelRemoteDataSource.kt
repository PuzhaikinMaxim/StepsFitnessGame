package com.puj.stepsfitnessgame.data.network.duel

import androidx.lifecycle.LiveData

interface DuelRemoteDataSource {

    fun startDuelSearch(): LiveData<Boolean>

    fun stopDuelSearch()

    fun updateStepAmount(): Boolean
}