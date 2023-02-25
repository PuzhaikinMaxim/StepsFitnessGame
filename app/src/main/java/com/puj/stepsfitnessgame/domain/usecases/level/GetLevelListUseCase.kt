package com.puj.stepsfitnessgame.domain.usecases.level

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel
import com.puj.stepsfitnessgame.domain.repositories.LevelRepository

class GetLevelListUseCase(
    private val repository: LevelRepository
) {

    operator fun invoke(): LiveData<List<DungeonLevel>> {
        return repository.getLevelList()
    }
}