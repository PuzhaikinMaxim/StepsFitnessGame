package com.puj.stepsfitnessgame.domain.usecases.duel

import com.puj.stepsfitnessgame.domain.repositories.DuelRepository

class CancelDuelUseCase(private val duelRepository: DuelRepository) {

    suspend operator fun invoke(): Boolean {
        return duelRepository.cancelDuel()
    }
}