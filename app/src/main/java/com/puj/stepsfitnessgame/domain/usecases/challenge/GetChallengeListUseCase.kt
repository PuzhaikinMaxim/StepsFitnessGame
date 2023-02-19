package com.puj.stepsfitnessgame.domain.usecases.challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import com.puj.stepsfitnessgame.domain.repositories.ChallengeRepository

class GetChallengeListUseCase(
    private val repository: ChallengeRepository
    ) {

    operator fun invoke(): MutableLiveData<List<Challenge>> {
        return repository.getChallengesList()
    }
}