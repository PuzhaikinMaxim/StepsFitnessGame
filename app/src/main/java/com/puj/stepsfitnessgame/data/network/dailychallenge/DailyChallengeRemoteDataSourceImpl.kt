package com.puj.stepsfitnessgame.data.network.dailychallenge

import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.data.network.StepCount
import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge
import java.time.OffsetDateTime

class DailyChallengeRemoteDataSourceImpl(
    private val token: String
): DailyChallengeRemoteDataSource {

    private val dailyChallengeApiService = ServiceFactory.create(DailyChallengeApiService::class.java)

    override suspend fun getDailyChallengeList(): List<DailyChallengeDto> {
        return try {
            val response = dailyChallengeApiService.getDailyTasksList(token)
            if(response.isSuccessful){
                response.body() ?: ArrayList()
            } else{
                generateDailyChallengeList()
                dailyChallengeApiService.getDailyTasksList(token).body() ?: ArrayList()
            }
        } catch (ex: Exception) {
            ArrayList()
        }
    }

    override suspend fun generateDailyChallengeList() {
        try {
            dailyChallengeApiService.generateDailyChallengeList(
                token,
                OffsetDateTime.now().toString()
            )
        }
        catch (ex: Exception){

        }
    }

    override suspend fun updateDailyChallengesProgress(stepCount: Int): Boolean {
        try {
            val response = dailyChallengeApiService.updateProgress(token, StepCount(stepCount))
            if(!response.isSuccessful || response.body() == null) return false
            if(response.body() == "Daily challenges is outdated") return true
            if(response.body() == "Daily challenges not found") return true
            return false
        }
        catch (ex: Exception){
            return false
        }
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