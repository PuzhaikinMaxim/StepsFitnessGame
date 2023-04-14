package com.puj.stepsfitnessgame.domain.usecases.playerstatistics

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics
import com.puj.stepsfitnessgame.domain.repositories.PlayerStatisticsRepository

class GetDuelStatisticsUseCase(private val playerStatisticsRepository: PlayerStatisticsRepository) {

    operator fun invoke(): LiveData<DuelStatistics> {
        return playerStatisticsRepository.getDuelStatistics()
    }
}