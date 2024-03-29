package com.puj.stepsfitnessgame.data.network.duel


import com.puj.stepsfitnessgame.data.network.StepCount
import com.puj.stepsfitnessgame.data.network.challenge.CompletedChallengeRewardDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface DuelApiService {

    @GET("duel/get_duel")
    suspend fun getDuel(@Header("token") token: String): Response<DuelDto>

    @PUT("duel/claim_reward")
    suspend fun claimReward(@Header("token") token: String): Response<FinishedDuelRewardDto>

    @PUT("duel/cancel_duel")
    suspend fun cancelDuel(@Header("token") token: String): Response<Boolean>

    @PUT("duel/update_progress")
    suspend fun updateProgress(@Header("token") token: String, @Body stepCount: StepCount)

    @GET("duel/is_duel_not_finished")
    suspend fun isDuelNotFinished(@Header("token") token: String): Response<Boolean>
}