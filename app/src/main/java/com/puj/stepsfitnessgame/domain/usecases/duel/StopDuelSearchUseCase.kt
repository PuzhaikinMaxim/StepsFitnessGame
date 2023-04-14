package com.puj.stepsfitnessgame.domain.usecases.duel

import com.puj.stepsfitnessgame.domain.repositories.DuelRepository

class StopDuelSearchUseCase(private val repository: DuelRepository) {

    operator fun invoke() {
        repository.stopDuelSearch()
    }
}