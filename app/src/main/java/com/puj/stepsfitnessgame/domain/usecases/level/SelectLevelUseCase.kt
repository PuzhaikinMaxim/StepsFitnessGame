package com.puj.stepsfitnessgame.domain.usecases.level

import com.puj.stepsfitnessgame.domain.repositories.LevelRepository

class SelectLevelUseCase(
    private val repository: LevelRepository
) {

    operator fun invoke(level: Int) {
        repository.selectLevel(level)
    }
}