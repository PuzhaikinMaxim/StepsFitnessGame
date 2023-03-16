package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ActiveChallengeMapper {

    fun mapActiveChallengeDtoToChallenge(activeChallengeDto: ActiveChallengeDto?): Challenge? {
        if(activeChallengeDto == null) return null

        /*
        val challengeEndDateTime = LocalDateTime.parse(activeChallengeDto.challengeEndDateTime)

        println(challengeEndDateTime)
        println(LocalDateTime.now())

        val timeTillEndText: String

        val minutesDiff = ChronoUnit.MINUTES.between(
            challengeEndDateTime, LocalDateTime.now()
        )

        timeTillEndText = if(challengeEndDateTime < LocalDateTime.now()){
            "Время окончено"
        } else{
            if(minutesDiff / 60 > 0){
                "${minutesDiff/60}ч ${minutesDiff%60}м"
            } else {
                "${minutesDiff}м"
            }
        }

         */
        println(activeChallengeDto.isCompleted)

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