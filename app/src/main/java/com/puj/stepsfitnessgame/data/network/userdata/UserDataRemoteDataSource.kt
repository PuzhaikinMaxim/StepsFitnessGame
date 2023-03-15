package com.puj.stepsfitnessgame.data.network.userdata

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.userdata.UserData

interface UserDataRemoteDataSource {

    suspend fun getUserLevel(): Response<UserData>
}