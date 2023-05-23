package com.puj.stepsfitnessgame.data.network.playerstatistics

import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.data.network.StepCount
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics

class PlayerStatisticsDataProviderImpl(private val token: String) : PlayerStatisticsDataProvider {

    private val playerStatisticsApiService = ServiceFactory.create(
        PlayerStatisticsApiService::class.java
    )

    override suspend fun getDuelStatistics(): Response<DuelStatistics> {
        try {
            val response = playerStatisticsApiService.getDuelStatistics(token)
            if(response.isSuccessful && response.body() != null){
                return Response.Success(response.body()!!)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        } catch (ex: Exception) {
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun updateProgress(stepCount: Int) {
        try {
            playerStatisticsApiService.updateStepCount(token, StepCount(stepCount))
        } catch (ex: Exception) {

        }
    }
}