package com.puj.stepsfitnessgame.domain.usecases.auth

import com.puj.stepsfitnessgame.domain.repositories.UserRepository
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.authresult.AuthResult

class GetIsUserLoggedInUseCase(
    private val repository: UserRepository
    ) {

    suspend operator fun invoke(): AuthResult{
        return repository.isUserLoggedIn()
    }
}