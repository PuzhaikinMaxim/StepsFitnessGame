package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

interface ChallengeRepository {

    fun getChallengesList(): MutableLiveData<List<Challenge>>

    suspend fun startChallenge(challengeId: Int)

    suspend fun cancelActiveChallenge()
}