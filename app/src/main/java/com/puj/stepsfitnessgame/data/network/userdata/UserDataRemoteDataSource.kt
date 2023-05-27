package com.puj.stepsfitnessgame.data.network.userdata

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.userdata.UserData
import com.puj.stepsfitnessgame.domain.models.userdata.UserEditProfileData
import com.puj.stepsfitnessgame.domain.models.userdata.UserProfileData

interface UserDataRemoteDataSource {

    suspend fun getUserLevel(): Response<UserData>

    suspend fun getUserProfileData(): Response<UserProfileData>

    suspend fun getEditProfileData(): Response<UserEditProfileData>
}