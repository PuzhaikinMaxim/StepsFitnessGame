package com.puj.stepsfitnessgame.data.network.userdata

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.userdata.UserData

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
}