package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward

interface ChallengeRepository {

    fun getChallengesList(): MutableLiveData<List<Challenge>>

    suspend fun startChallenge(challengeId: Int)

    suspend fun cancelActiveChallenge()

    suspend fun endActiveChallenge(): CompletedChallengeReward?
}