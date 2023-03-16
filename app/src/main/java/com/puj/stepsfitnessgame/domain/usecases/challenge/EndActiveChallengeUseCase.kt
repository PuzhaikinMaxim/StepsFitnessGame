package com.puj.stepsfitnessgame.domain.usecases.challenge

import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeData
import com.puj.stepsfitnessgame.domain.repositories.ChallengeRepository
import java.time.LocalDateTime
import java.time.OffsetDateTime

class EndActiveChallengeUseCase(
    private val repository: ChallengeRepository
) {

    suspend operator fun invoke(): CompletedChallengeData? {
        return repository.endActiveChallenge()
    }
}