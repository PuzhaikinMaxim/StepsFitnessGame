package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

interface ChallengeRemoteDataSource {
    suspend fun updateChallengeProgress(stepCount: Int)

    suspend fun getChallengesListByLevel(challengeLevel: Int): Response<List<Challenge>>

    suspend fun getActiveChallenge(): Challenge?

    suspend fun startChallenge(challengeId: Int): Response<Unit>

    suspend fun cancelActiveChallenge(): Response<Unit>
}