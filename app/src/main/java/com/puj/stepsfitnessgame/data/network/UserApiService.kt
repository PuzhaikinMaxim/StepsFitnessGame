package com.puj.stepsfitnessgame.data.network

import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {

    @GET("test")
    fun test(): Call<String>

    @POST("registerUser")
    fun registerUser(@Body userRegistrationInfo: UserRegistrationInfo): Call<String>

    @POST("loginUser")
    fun loginUser(userCredentials: UserCredentials): Call<String>
}