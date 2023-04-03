package com.puj.stepsfitnessgame.data.network.achievement

import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response

class AchievementRemoteDataSourceImpl(private val token: String): AchievementRemoteDataSource {

    private val achievementApiService = ServiceFactory.create(AchievementApiService::class.java)

    override suspend fun getAchievementList(): Response<List<AchievementDto>> {
        try {
            val response = achievementApiService.getAchievementList(token)
            if(response.isSuccessful && response.body() != null){
                return Response.Success(response.body()!!)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception){
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }
}