package com.puj.stepsfitnessgame.data.network.dailychallenge

import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge

class FakeDailyChallengeRemoteDataSource(
    token: String
): DailyChallengeRemoteDataSource {

    private var dailyTaskProgress = 200

    private val dailyChallenges = arrayListOf(
        DailyChallengeDto(200, true),
        DailyChallengeDto(500, false),
        DailyChallengeDto(1000, false),
        DailyChallengeDto(5000, false),
        DailyChallengeDto(10000, false),
        DailyChallengeDto(15000, false),
    )

    override suspend fun getDailyChallengeList(): List<DailyChallengeDto> {
        return dailyChallenges
    }

    override suspend fun updateDailyChallengesProgress(stepCount: Int): Boolean {
        dailyTaskProgress += stepCount
        if(dailyTaskProgress >= 500) dailyChallenges[1] = dailyChallenges[1].copy(isCompleted = true)
        if(dailyTaskProgress >= 1000) dailyChallenges[2] = dailyChallenges[2].copy(isCompleted = true)
        if(dailyTaskProgress >= 5000) dailyChallenges[3] = dailyChallenges[3].copy(isCompleted = true)
        if(dailyTaskProgress >= 10000) dailyChallenges[4] = dailyChallenges[4].copy(isCompleted = true)
        if(dailyTaskProgress >= 15000) dailyChallenges[4] = dailyChallenges[4].copy(isCompleted = true)
        return true
    }

    override suspend fun claimDailyChallengesReward(): CompletedDailyChallengeRewardDto {
        TODO("Not yet implemented")
    }

    override suspend fun generateDailyChallengeList() {
        TODO("Not yet implemented")
    }
}