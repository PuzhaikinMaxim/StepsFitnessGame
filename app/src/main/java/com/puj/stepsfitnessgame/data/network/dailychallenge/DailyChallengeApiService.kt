package com.puj.stepsfitnessgame.data.network.dailychallenge

import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.time.OffsetDateTime

interface DailyChallengeApiService {

    @GET("daily_challenges/get_daily_challenges_list")
    suspend fun getDailyTasksList(@Header("token") token: String): Response<List<DailyChallengeDto>>

    @GET("daily_challenges/claim_daily_challenges_reward")
    suspend fun claimDailyChallengesReward(
        @Header("token") token: String
    ): Response<CompletedDailyChallengeRewardDto>

    @POST("daily_challenges/generate_daily_challenge_list")
    suspend fun generateDailyChallengeList(
        @Header("token") token: String,
        @Body offsetDateTime: String
    )
}