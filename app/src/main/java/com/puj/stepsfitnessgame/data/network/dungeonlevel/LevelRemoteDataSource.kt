package com.puj.stepsfitnessgame.data.network.dungeonlevel

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel

interface LevelRemoteDataSource {

    suspend fun getLevelList(): Response<List<DungeonLevelDto>>
}