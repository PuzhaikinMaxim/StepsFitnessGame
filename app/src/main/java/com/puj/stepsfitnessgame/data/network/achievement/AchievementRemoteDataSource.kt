package com.puj.stepsfitnessgame.data.network.achievement

import com.puj.stepsfitnessgame.domain.models.Response

interface AchievementRemoteDataSource {

    suspend fun getAchievementList(): Response<List<AchievementDto>>
}