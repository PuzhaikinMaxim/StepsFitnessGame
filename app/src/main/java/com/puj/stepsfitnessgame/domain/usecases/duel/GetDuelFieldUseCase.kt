package com.puj.stepsfitnessgame.domain.usecases.duel

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository

class GetDuelFieldUseCase(private val repository: DuelRepository) {

    operator fun invoke(): LiveData<DuelField> {
        return repository.getDuelField()
    }
}