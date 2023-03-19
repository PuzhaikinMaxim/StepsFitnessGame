package com.puj.stepsfitnessgame.data.network.characteristics

import com.puj.stepsfitnessgame.domain.models.characteristics.Characteristics
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface CharacteristicsApiService {

    @GET("player/get_characteristics")
    suspend fun getCharacteristics(@Header("token") token: String): Response<Characteristics>

    @PUT("player/increase_endurance")
    suspend fun increaseEndurance(@Header("token") token: String)

    @PUT("player/increase_strength")
    suspend fun increaseStrength(@Header("token") token: String)
}