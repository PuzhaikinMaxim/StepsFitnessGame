package com.puj.stepsfitnessgame.data.network.achievement

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AchievementApiService {

    @GET("statistics/get_achievements")
    suspend fun getAchievementList(@Header("token") token: String): Response<List<AchievementDto>>
}