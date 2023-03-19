package com.puj.stepsfitnessgame.domain.usecases.dailychallenge

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge
import com.puj.stepsfitnessgame.domain.repositories.DailyChallengesRepository

class GetDailyChallengesUseCase(
    private val dailyChallengesRepository: DailyChallengesRepository
) {

    operator fun invoke(): LiveData<List<DailyChallenge>> {
        return dailyChallengesRepository.getDailyChallengesList()
    }
}