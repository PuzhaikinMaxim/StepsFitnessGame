package com.puj.stepsfitnessgame.data.network.dungeonlevel

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface LevelApiService {

    @GET("challenge_level/get_challenge_levels")
    suspend fun getChallengeLevels(@Header("token") token: String): Response<List<DungeonLevelDto>>
}