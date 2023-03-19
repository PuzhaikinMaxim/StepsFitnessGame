package com.puj.stepsfitnessgame.data.network.dungeonlevel

import com.puj.stepsfitnessgame.data.AppErrorCodes.Companion.DEFAULT_ERROR_CODE
import com.puj.stepsfitnessgame.data.AppErrorCodes.Companion.SERVER_NOT_RESPONDING_CODE
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response

class LevelRemoteDataSourceImpl(
    private val token: String
): LevelRemoteDataSource {

    private val levelApiService = ServiceFactory.create(LevelApiService::class.java)

    override suspend fun getLevelList(): Response<List<DungeonLevelDto>> {
        try {
            val response = levelApiService.getChallengeLevels(token)
            if(response.isSuccessful){
                val list = response.body() ?: ArrayList()
                return Response.Success(list)
            }
            return Response.Error(DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception) {
            return Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }
}