package com.puj.stepsfitnessgame.data.network.playerstatistics

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics

interface PlayerStatisticsDataProvider {

    suspend fun getDuelStatistics(): Response<DuelStatistics>

    suspend fun updateProgress(stepCount: Int)
}