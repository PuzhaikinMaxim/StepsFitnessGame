package com.puj.stepsfitnessgame.data.network.challengelevel

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ChallengeLevelApiService {

    @GET("challenge_level/get_challenge_levels")
    suspend fun getChallengeLevels(@Header("token") token: String): Response<List<ChallengeLevelDto>>
}