package com.puj.stepsfitnessgame.data.network.userdata

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.userdata.UserData
import com.puj.stepsfitnessgame.domain.models.userdata.UserEditProfileData
import com.puj.stepsfitnessgame.domain.models.userdata.UserProfileData

class FakeUserDataRemoteDataSource(
    private val token: String
): UserDataRemoteDataSource {

    override suspend fun getUserLevel(): Response<UserData> {
        return Response.Success(UserData(
            "username",
            5,
            100,
            200
        ))
    }

    override suspend fun getUserProfileData(): Response<UserProfileData> {
        TODO("Not yet implemented")
    }

    override suspend fun getEditProfileData(): Response<UserEditProfileData> {
        TODO("Not yet implemented")
    }
}