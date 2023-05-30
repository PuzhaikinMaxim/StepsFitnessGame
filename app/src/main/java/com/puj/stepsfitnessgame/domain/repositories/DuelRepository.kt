package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.data.network.duel.FinishedDuelRewardDto
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics
import com.puj.stepsfitnessgame.domain.models.duel.FinishedDuelReward

interface DuelRepository {

    fun startDuelSearch()

    fun stopDuelSearch()

    fun getDuelField(): LiveData<DuelField>

    fun getIsOpponentFound(): LiveData<Boolean>

    fun getDuelStatistics(): LiveData<DuelStatistics>

    suspend fun getIsDuelNotFinished(): Boolean

    suspend fun claimReward(): FinishedDuelReward?

    suspend fun cancelDuel(): Boolean
}