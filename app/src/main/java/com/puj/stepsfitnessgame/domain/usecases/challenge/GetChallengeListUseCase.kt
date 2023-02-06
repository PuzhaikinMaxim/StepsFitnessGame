package com.puj.stepsfitnessgame.domain.usecases.challenge

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import com.puj.stepsfitnessgame.domain.repositories.ChallengeRepository

class GetChallengeListUseCase(
    private val repository: ChallengeRepository
    ) {

    operator fun invoke(): LiveData<List<Challenge>> {
        return repository.getChallengesList()
    }
}