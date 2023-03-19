package com.puj.stepsfitnessgame.domain.usecases.dailychallenge

import com.puj.stepsfitnessgame.domain.models.dailychallenge.CompletedDailyChallengeReward
import com.puj.stepsfitnessgame.domain.repositories.DailyChallengesRepository

class ClaimDailyChallengeRewardUseCase(
    private val repository: DailyChallengesRepository
) {

    suspend operator fun invoke(): CompletedDailyChallengeReward? {
        return repository.claimDailyChallengesReward()
    }
}