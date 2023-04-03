package com.puj.stepsfitnessgame.data.network.challengelevel

import com.puj.stepsfitnessgame.domain.models.Response

interface ChallengeLevelRemoteDataSource {

    suspend fun getLevelList(): Response<List<ChallengeLevelDto>>
}