package com.puj.stepsfitnessgame.domain.usecases.auth

import com.puj.stepsfitnessgame.domain.UserRepository
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

class RegisterUserUseCase(
    private val userRepository: UserRepository
    ) {

    operator fun invoke(userRegistrationInfo: UserRegistrationInfo): Response<Unit> {
        return userRepository.registerUser(userRegistrationInfo)
    }
}