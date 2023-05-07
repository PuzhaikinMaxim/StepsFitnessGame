package com.puj.stepsfitnessgame.domain.usecases.userdata

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.userdata.UserProfileData
import com.puj.stepsfitnessgame.domain.repositories.UserDataRepository

class GetUserProfileDataUseCase(
    private val userDataRepository: UserDataRepository
) {

    operator fun invoke(): LiveData<UserProfileData> {
        return userDataRepository.getUserProfileData()
    }
}