package com.puj.stepsfitnessgame.domain.usecases.challenge

import com.puj.stepsfitnessgame.domain.repositories.ChallengeRepository

class StartChallengeUseCase(
    private val repository: ChallengeRepository
) {

    suspend operator fun invoke(challengeId: Int) {
        repository.startChallenge(challengeId)
    }
}