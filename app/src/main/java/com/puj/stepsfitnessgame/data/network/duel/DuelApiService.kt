package com.puj.stepsfitnessgame.data.network.duel


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface DuelApiService {

    @GET("duel/get_duel")
    suspend fun getDuel(@Header("token") token: String): Response<DuelDto>
}