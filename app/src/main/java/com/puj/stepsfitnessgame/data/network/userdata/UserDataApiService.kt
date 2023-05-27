package com.puj.stepsfitnessgame.data.network.userdata

import com.puj.stepsfitnessgame.domain.models.userdata.UserData
import com.puj.stepsfitnessgame.domain.models.userdata.UserEditProfileData
import com.puj.stepsfitnessgame.domain.models.userdata.UserProfileData
import com.puj.stepsfitnessgame.presentation.activities.EditProfileActivity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserDataApiService {

    @GET("player/get_player_data")
    suspend fun getUserData(@Header("token") token: String): Response<UserData>

    @GET("player/get_player_profile_data")
    suspend fun getUserProfileData(@Header("token") token: String): Response<UserProfileData>

    @GET("player/get_edit_profile_data")
    suspend fun getEditProfileData(@Header("token") token: String): Response<UserEditProfileData>
}