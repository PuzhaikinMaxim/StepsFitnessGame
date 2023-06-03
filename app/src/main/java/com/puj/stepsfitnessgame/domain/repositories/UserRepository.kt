package com.puj.stepsfitnessgame.domain.repositories

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.authresult.AuthResult
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo
import retrofit2.http.Body

interface UserRepository {

    suspend fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit>

    suspend fun loginUser(userCredentials: UserCredentials): Response<Unit>

    suspend fun isUserLoggedIn(): AuthResult

    suspend fun changeUsername(username: String): Boolean

    suspend fun setProfileImage(profileImageId: Int): Boolean

    suspend fun logOut()
}