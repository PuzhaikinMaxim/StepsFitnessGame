package com.puj.stepsfitnessgame.domain.usecases.challengelevel

import com.puj.stepsfitnessgame.domain.repositories.ChallengeLevelRepository

class SelectLevelUseCase(
    private val repository: ChallengeLevelRepository
) {

    operator fun invoke(level: Int) {
        repository.selectLevel(level)
    }
}