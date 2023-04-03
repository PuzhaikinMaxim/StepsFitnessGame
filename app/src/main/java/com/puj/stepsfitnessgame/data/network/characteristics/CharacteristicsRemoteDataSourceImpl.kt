package com.puj.stepsfitnessgame.data.network.characteristics

import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.characteristics.Characteristics

class CharacteristicsRemoteDataSourceImpl(
    private val token: String
): CharacteristicsRemoteDataSource {

    private val apiService = ServiceFactory.create(CharacteristicsApiService::class.java)

    override suspend fun getCharacteristics(): Response<Characteristics> {
        try {
            val response = apiService.getCharacteristics(token)
            if(response.isSuccessful){
                return Response.Success(response.body()!!)
            }
            return Response.Error(AppErrorCodes.UNAUTHORIZED_CODE)
        }
        catch (ex: Exception) {
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun increaseEndurance() {
        apiService.increaseEndurance(token)
    }

    override suspend fun increaseStrength() {
        apiService.increaseStrength(token)
    }
}