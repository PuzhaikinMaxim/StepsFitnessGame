package com.puj.stepsfitnessgame.domain.usecases.duel

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository

class GetIsOpponentFoundUseCase(private val repository: DuelRepository) {

    operator fun invoke(): LiveData<Boolean> {
        return repository.getIsOpponentFound()
    }
}