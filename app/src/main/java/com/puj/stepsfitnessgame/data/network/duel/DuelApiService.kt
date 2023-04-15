package com.puj.stepsfitnessgame.data.network.duel


import com.puj.stepsfitnessgame.data.network.challenge.CompletedChallengeRewardDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface DuelApiService {

    @GET("duel/get_duel")
    suspend fun getDuel(@Header("token") token: String): Response<DuelDto>

    suspend fun claimReward(@Header("token") token: String): Response<FinishedDuelRewardDto>

    suspend fun cancelDuel(@Header("token") token: String): Response<Boolean>
}