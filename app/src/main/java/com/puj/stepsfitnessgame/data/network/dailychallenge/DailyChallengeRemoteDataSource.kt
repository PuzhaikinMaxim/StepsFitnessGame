package com.puj.stepsfitnessgame.data.network.dailychallenge

import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge

interface DailyChallengeRemoteDataSource {

    suspend fun getDailyChallengeList(): List<DailyChallengeDto>

    suspend fun updateDailyChallengesProgress(stepCount: Int): Boolean

    suspend fun claimDailyChallengesReward(): CompletedDailyChallengeRewardDto?

    suspend fun generateDailyChallengeList()
}