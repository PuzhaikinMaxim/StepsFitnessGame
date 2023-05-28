package com.puj.stepsfitnessgame.data.network.user

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

interface UserRemoteDataSource {

    suspend fun loginUser(userCredentials: UserCredentials): Response<String>

    suspend fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit>

    suspend fun isUserLoggedIn(token: String): Response<Unit>

    suspend fun changeUsername(username: String): Response<Boolean>

    suspend fun setProfileImage(profileImageId: Int): Response<Boolean>

    suspend fun logOut()

    suspend fun test()
}