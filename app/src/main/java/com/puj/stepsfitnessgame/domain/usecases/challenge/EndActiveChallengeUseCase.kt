package com.puj.stepsfitnessgame.domain.usecases.challenge

import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward
import com.puj.stepsfitnessgame.domain.repositories.ChallengeRepository

class EndActiveChallengeUseCase(
    private val repository: ChallengeRepository
) {

    suspend operator fun invoke(): CompletedChallengeReward? {
        return repository.endActiveChallenge()
    }
}