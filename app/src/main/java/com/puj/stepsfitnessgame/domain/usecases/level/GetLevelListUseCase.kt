package com.puj.stepsfitnessgame.domain.usecases.level

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel
import com.puj.stepsfitnessgame.domain.repositories.DungeonLevelRepository

class GetLevelListUseCase(
    private val repository: DungeonLevelRepository
) {

    operator fun invoke(): LiveData<List<DungeonLevel>> {
        return repository.getLevelList()
    }
}