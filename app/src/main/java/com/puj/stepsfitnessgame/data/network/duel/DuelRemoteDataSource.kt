package com.puj.stepsfitnessgame.data.network.duel

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics

interface DuelRemoteDataSource {

    fun startDuelSearch(username: String)

    fun getIsOpponentFound(): LiveData<Boolean>

    fun stopDuelSearch()

    suspend fun updateStepAmount(amountOfSteps: Int): Boolean

    suspend fun getDuelField(): Response<DuelDto>

    suspend fun claimReward(): Response<FinishedDuelRewardDto>

    suspend fun cancelDuel(): Response<Boolean>

    suspend fun getIsDuelNotFinished(): Boolean

    fun tryFindGame()
}