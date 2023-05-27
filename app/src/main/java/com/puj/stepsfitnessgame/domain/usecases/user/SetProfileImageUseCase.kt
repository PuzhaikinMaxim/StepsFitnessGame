package com.puj.stepsfitnessgame.domain.usecases.user

import com.puj.stepsfitnessgame.domain.repositories.UserRepository

class SetProfileImageUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(profileImageId: Int): Boolean {
        return userRepository.setProfileImage(profileImageId)
    }
}