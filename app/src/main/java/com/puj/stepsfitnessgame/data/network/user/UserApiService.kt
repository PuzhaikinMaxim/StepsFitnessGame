package com.puj.stepsfitnessgame.data.network.user

import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {

    @GET("test")
    suspend fun test(): Response<String>

    @GET("authorized")
    suspend fun isUserLoggedIn(@Header("token") token: String): Response<String>

    @POST("registerUser")
    suspend fun registerUser(@Body userRegistrationInfo: UserRegistrationInfo): Response<String>

    @POST("login")
    suspend fun loginUser(@Body userCredentials: UserCredentials): Response<String>

    @PUT("change_username")
    suspend fun changeUsername(
        @Header("token") token: String,
        @Body usernameChangeRequest: UsernameChangeRequest
    ): Response<String>

    @PUT("player/set_player_profile_image/{imageId}")
    suspend fun setPlayerProfileImage(
        @Header("token") token: String,
        @Path("imageId") imageId: Int
    ): Response<Boolean>

    @PUT("logout")
    suspend fun logOut(@Header("token") token: String)
}