package com.puj.stepsfitnessgame.domain.usecases.auth

import com.puj.stepsfitnessgame.domain.UserRepository
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials

class LoginUserUseCase(private val userRepository: UserRepository) {

    operator fun invoke(userCredentials: UserCredentials): Response<Unit> {
        return userRepository.loginUser(userCredentials)
    }
}