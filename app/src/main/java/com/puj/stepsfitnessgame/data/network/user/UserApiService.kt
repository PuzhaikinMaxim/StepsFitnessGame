package com.puj.stepsfitnessgame.data.network.user

import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApiService {

    @GET("test")
    suspend fun test(): Response<String>

    @GET("authorized")
    suspend fun isUserLoggedIn(@Header("token") token: String): Response<String>

    @POST("registerUser")
    suspend fun registerUser(@Body userRegistrationInfo: UserRegistrationInfo): Response<String>

    @POST("login")
    suspend fun loginUser(@Body userCredentials: UserCredentials): Response<String>
}