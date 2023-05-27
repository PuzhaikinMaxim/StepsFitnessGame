package com.puj.stepsfitnessgame.domain.usecases.userdata

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.userdata.UserEditProfileData
import com.puj.stepsfitnessgame.domain.repositories.UserDataRepository

class GetUserEditProfileDataUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(): UserEditProfileData? {
        return userDataRepository.getUserEditProfileData()
    }
}