package com.puj.stepsfitnessgame.domain.usecases.level

import com.puj.stepsfitnessgame.domain.repositories.DungeonLevelRepository

class SelectLevelUseCase(
    private val repository: DungeonLevelRepository
) {

    operator fun invoke(level: Int) {
        repository.selectLevel(level)
    }
}