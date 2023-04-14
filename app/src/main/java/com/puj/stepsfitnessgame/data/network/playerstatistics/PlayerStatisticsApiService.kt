package com.puj.stepsfitnessgame.data.network.playerstatistics

import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PlayerStatisticsApiService {

    @GET("statistics/get_duel_statistics")
    suspend fun getDuelStatistics(@Header("token") token: String): Response<DuelStatistics>
}