package com.puj.stepsfitnessgame.data.network.dailychallenge

import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge

class DailyChallengeRemoteDataSourceImpl(
    private val token: String
): DailyChallengeRemoteDataSource {

    private val dailyChallengeApiService = ServiceFactory.create(DailyChallengeApiService::class.java)

    override suspend fun getDailyChallengeList(): List<DailyChallengeDto> {
        try {
            val response = dailyChallengeApiService.getDailyTasksList(token)
            if(response.isSuccessful){
                return response.body() ?: ArrayList()
            }
            return ArrayList()
        }
        catch (ex: Exception) {
            return ArrayList()
        }
    }

    override suspend fun updateDailyChallengesProgress(stepCount: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun claimDailyChallengesReward(): CompletedDailyChallengeRewardDto? {
        try {
            val response = dailyChallengeApiService.claimDailyChallengesReward(token)
            if(response.isSuccessful){
                return response.body()
            }
            return null
        }
        catch (ex: Exception) {
            return null
        }
    }
}