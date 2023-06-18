package com.puj.stepsfitnessgame.domain.usecases.challenge

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.challenge.ChallengeStatistics
import com.puj.stepsfitnessgame.domain.repositories.ChallengeRepository

class GetChallengeStatisticsUseCase(
    private val challengeRepository: ChallengeRepository
) {

    operator fun invoke(): LiveData<ChallengeStatistics> {
        return challengeRepository.getChallengeStatistics()
    }
}