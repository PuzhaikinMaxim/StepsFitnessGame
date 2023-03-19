package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ActiveChallengeMapper {

    fun mapActiveChallengeDtoToChallenge(activeChallengeDto: ActiveChallengeDto?): Challenge? {
        if(activeChallengeDto == null) return null
        return Challenge(
            activeChallengeDto.challengeId,
            activeChallengeDto.challengeName,
            activeChallengeDto.progress,
            activeChallengeDto.amountOfPoints,
            activeChallengeDto.amountOfSteps,
            activeChallengeDto.challengeEndDateTime,
            isShown = true,
            isStarted = true,
            isCompleted = activeChallengeDto.isCompleted
        )
    }
}