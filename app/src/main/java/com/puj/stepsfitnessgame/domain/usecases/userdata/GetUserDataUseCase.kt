package com.puj.stepsfitnessgame.domain.usecases.userdata

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.userdata.UserData
import com.puj.stepsfitnessgame.domain.repositories.UserDataRepository

class GetUserDataUseCase(
    private val repository: UserDataRepository
) {

    operator fun invoke(): LiveData<UserData> {
        return repository.getUserData()
    }
}