package com.puj.stepsfitnessgame.data.network.playerstatistics

import com.puj.stepsfitnessgame.data.network.StepCount
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface PlayerStatisticsApiService {

    @GET("statistics/get_duel_statistics")
    suspend fun getDuelStatistics(@Header("token") token: String): Response<DuelStatistics>

    @PUT("statistics/update_step_count")
    suspend fun updateStepCount(@Header("token") token: String, @Body stepCount: StepCount)
}