package com.puj.stepsfitnessgame.data.network.level

import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel

interface LevelRemoteDataSource {

    fun getLevelList(): List<DungeonLevel>
}