package com.puj.stepsfitnessgame.domain.usecases.user

import com.puj.stepsfitnessgame.domain.repositories.UserRepository

class ChangeUsernameUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(username: String): Boolean {
        return userRepository.changeUsername(username)
    }
}