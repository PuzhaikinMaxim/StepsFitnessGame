package com.puj.stepsfitnessgame.domain

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo
import retrofit2.http.Body

interface UserRepository {

    fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit>

    fun loginUser(userCredentials: UserCredentials): Response<Unit>
}