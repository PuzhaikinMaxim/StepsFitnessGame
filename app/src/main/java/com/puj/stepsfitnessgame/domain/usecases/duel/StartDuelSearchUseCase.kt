package com.puj.stepsfitnessgame.domain.usecases.duel

import com.puj.stepsfitnessgame.domain.repositories.DuelRepository

class StartDuelSearchUseCase(private val duelRepository: DuelRepository) {

    operator fun invoke() {
        duelRepository.startDuelSearch()
    }
}