package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.data.network.StepCount
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import com.puj.stepsfitnessgame.domain.models.challenge.ChallengeStatistics
import retrofit2.Response
import retrofit2.http.*

interface ChallengeApiService {

    @PUT("active_challenges/update_user_progress")
    suspend fun updateChallengeProgress(
        @Header("token") enterToken: String,
        @Body stepCount: StepCount
    ): Response<String>

    @GET("challenges/challenge_statistics/{level}")
    suspend fun getChallengeStatistics(
        @Header("token") enterToken: String,
        @Path("level") challengeLevel: Int
    ): Response<ChallengeStatistics>

    @GET("challenges/{level}")
    suspend fun getChallengesListByLevel(
        @Header("token") enterToken: String,
        @Path("level") challengeLevel: Int
    ): Response<List<Challenge>>

    @GET("active_challenges/active_challenge")
    suspend fun getActiveChallenge(
        @Header("token") enterToken: String
    ): Response<ActiveChallengeDto?>

    @GET("active_challenges/end_challenge")
    suspend fun endActiveChallenge(
        @Header("token") enterToken: String
    ): Response<CompletedChallengeRewardDto>

    @POST("active_challenges/start_challenge/{challenge_id}")
    suspend fun startChallenge(
        @Header("token") enterToken: String,
        @Path("challenge_id") challengeId: Int
    ): Response<String>

    @DELETE("active_challenges/cancel_active_challenge")
    suspend fun cancelActiveChallenge(
        @Header("token") enterToken: String
    ): Response<String>
}