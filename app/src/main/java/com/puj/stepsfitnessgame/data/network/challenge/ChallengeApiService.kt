package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import retrofit2.Response
import retrofit2.http.*

interface ChallengeApiService {

    @PUT("active_challenges/update_user_progress")
    suspend fun updateChallengeProgress(
        @Header("token") enterToken: String,
        @Body stepCount: Int
    ): Response<String>

    @GET("challenges/{level}")
    suspend fun getChallengesListByLevel(
        @Header("token") enterToken: String,
        @Path("level") challengeLevel: Int
    ): Response<List<Challenge>>

    @GET("active_challenges/active_challenge")
    suspend fun getActiveChallenge(
        @Header("token") enterToken: String
    ): Response<Challenge?>

    @POST("active_challenges/start_challenge")
    suspend fun startChallenge(
        @Header("token") enterToken: String,
        @Body challengeId: Int
    ): Response<String>

    @DELETE("active_challenges/cancel_active_challenge")
    suspend fun cancelActiveChallenge(
        @Header("token") enterToken: String
    ): Response<String>
}