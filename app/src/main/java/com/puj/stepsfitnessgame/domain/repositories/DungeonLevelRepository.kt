package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel

interface DungeonLevelRepository {

    fun getLevelList(): LiveData<List<DungeonLevel>>

    fun selectLevel(level: Int)
}