package com.puj.stepsfitnessgame.domain.usecases.auth

import com.puj.stepsfitnessgame.domain.repositories.UserRepository
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

class RegisterUserUseCase(
    private val userRepository: UserRepository
    ) {

    suspend operator fun invoke(userRegistrationInfo: UserRegistrationInfo): Response<Unit> {
        return userRepository.registerUser(userRegistrationInfo)
    }
}