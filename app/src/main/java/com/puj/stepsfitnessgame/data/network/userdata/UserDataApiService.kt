package com.puj.stepsfitnessgame.data.network.userdata

import com.puj.stepsfitnessgame.domain.models.userdata.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserDataApiService {

    @GET("player/get_player_data")
    suspend fun getUserData(@Header("token") token: String): Response<UserData>
}