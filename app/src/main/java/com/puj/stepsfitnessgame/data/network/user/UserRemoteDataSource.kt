package com.puj.stepsfitnessgame.data.network.user

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

interface UserRemoteDataSource {
    fun loginUser(userCredentials: UserCredentials): Response<String>

    fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit>

    fun isUserLoggedIn(token: String): Response<Unit>

    fun test()
}