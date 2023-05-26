package com.puj.stepsfitnessgame.data.network.rating

import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response

class RatingDataSourceImpl(private val token: String): RatingDataSource {

    private val service = ServiceFactory.create(RatingApiService::class.java)

    override suspend fun getStepAmountRating(): Response<List<RatingDto>> {
        try {
            val response = service.getTopOneHundredPlayersByStepAmount(token)
            if(response.isSuccessful && response.body() != null){
                return Response.Success(response.body()!!)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception){
            ex.printStackTrace()
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun getDuelsAmountRating(): Response<List<RatingDto>> {
        try {
            val response = service.getTopOneHundredPlayersByAmountOfDuelsWon(token)
            if(response.isSuccessful && response.body() != null){
                return Response.Success(response.body()!!)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception){
            ex.printStackTrace()
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }
}