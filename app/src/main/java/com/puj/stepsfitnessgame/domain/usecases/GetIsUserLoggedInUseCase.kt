package com.puj.stepsfitnessgame.domain.usecases

import com.puj.stepsfitnessgame.domain.UserRepository
import com.puj.stepsfitnessgame.domain.models.Response

class GetIsUserLoggedInUseCase(private val repository: UserRepository) {

    operator fun invoke(): Response<Unit>{
        return repository.isUserLoggedIn()
    }
}