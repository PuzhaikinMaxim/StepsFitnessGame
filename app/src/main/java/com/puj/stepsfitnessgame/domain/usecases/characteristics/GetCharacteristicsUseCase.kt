package com.puj.stepsfitnessgame.domain.usecases.characteristics

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.characteristics.Characteristics
import com.puj.stepsfitnessgame.domain.repositories.CharacteristicsRepository

class GetCharacteristicsUseCase(
    private val repository: CharacteristicsRepository
) {

    operator fun invoke(): LiveData<Characteristics> {
        return repository.getCharacteristics()
    }
}