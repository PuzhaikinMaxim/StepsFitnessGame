package com.puj.stepsfitnessgame.domain.usecases.user

import com.puj.stepsfitnessgame.domain.repositories.UserRepository

class LogOutUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() {
        userRepository.logOut()
    }
}