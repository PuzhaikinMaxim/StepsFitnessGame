package com.puj.stepsfitnessgame.domain.usecases.auth

import com.puj.stepsfitnessgame.domain.repositories.UserRepository
import com.puj.stepsfitnessgame.domain.models.Response

class GetIsUserLoggedInUseCase(
    private val repository: UserRepository
    ) {

    suspend operator fun invoke(): Response<Unit>{
        return repository.isUserLoggedIn()
    }
}