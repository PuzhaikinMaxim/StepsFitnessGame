package com.puj.stepsfitnessgame.domain.usecases.characteristics

import com.puj.stepsfitnessgame.domain.repositories.CharacteristicsRepository

class IncreaseStrengthUseCase(
    private val repository: CharacteristicsRepository
) {

    suspend operator fun invoke() {
        repository.increaseStrength()
    }
}