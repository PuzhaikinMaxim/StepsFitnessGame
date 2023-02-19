package com.puj.stepsfitnessgame.domain.usecases.challenge

import com.puj.stepsfitnessgame.domain.repositories.ChallengeRepository

class CancelActiveChallengeUseCase(
    private val repository: ChallengeRepository
) {

    suspend operator fun invoke() {
        repository.cancelActiveChallenge()
    }
}