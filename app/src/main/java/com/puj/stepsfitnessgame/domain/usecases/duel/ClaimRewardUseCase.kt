package com.puj.stepsfitnessgame.domain.usecases.duel

import com.puj.stepsfitnessgame.domain.models.duel.FinishedDuelReward
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository

class ClaimRewardUseCase(private val duelRepository: DuelRepository) {

    suspend operator fun invoke(): FinishedDuelReward? {
        return duelRepository.claimReward()
    }
}