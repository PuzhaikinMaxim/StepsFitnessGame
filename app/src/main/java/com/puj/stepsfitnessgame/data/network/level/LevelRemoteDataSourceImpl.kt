package com.puj.stepsfitnessgame.data.network.level

import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.data.network.challenge.ChallengeApiService
import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel

class LevelRemoteDataSourceImpl(
    private val token: String
): LevelRemoteDataSource {

    private val levelApiService = ServiceFactory.create(ChallengeApiService::class.java)

    override fun getLevelList(): List<DungeonLevel> {
        TODO("Not yet implemented")
    }
}