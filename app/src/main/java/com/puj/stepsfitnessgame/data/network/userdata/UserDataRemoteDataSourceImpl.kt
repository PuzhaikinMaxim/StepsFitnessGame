package com.puj.stepsfitnessgame.data.network.userdata

import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.userdata.UserData

class UserDataRemoteDataSourceImpl(
    private val token: String
): UserDataRemoteDataSource {

    private val userDataApiService = ServiceFactory.create(UserDataApiService::class.java)

    override suspend fun getUserLevel(): Response<UserData> {
        try {
            val response = userDataApiService.getUserData(token)
            if(response.isSuccessful){
                val userData = response.body()
                if(userData != null){
                    return Response.Success(userData)
                }
                return Response.Error(404)
            }
            return Response.Error(404)
        }
        catch (ex: Exception) {
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }
}