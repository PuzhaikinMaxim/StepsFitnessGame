package com.puj.stepsfitnessgame.data.network.challengelevel

import com.puj.stepsfitnessgame.data.network.AppErrorCodes.Companion.DEFAULT_ERROR_CODE
import com.puj.stepsfitnessgame.data.network.AppErrorCodes.Companion.SERVER_NOT_RESPONDING_CODE
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response

class ChallengeLevelRemoteDataSourceImpl(
    private val token: String
): ChallengeLevelRemoteDataSource {

    private val challengeLevelApiService = ServiceFactory.create(ChallengeLevelApiService::class.java)

    override suspend fun getLevelList(): Response<List<ChallengeLevelDto>> {
        try {
            val response = challengeLevelApiService.getChallengeLevels(token)
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