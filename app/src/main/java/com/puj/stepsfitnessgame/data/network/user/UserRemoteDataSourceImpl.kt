package com.puj.stepsfitnessgame.data.network.user

import com.puj.stepsfitnessgame.data.AppErrorCodes.Companion.SERVER_NOT_RESPONDING_CODE
import com.puj.stepsfitnessgame.data.AppErrorCodes.Companion.UNAUTHORIZED_CODE
import com.puj.stepsfitnessgame.data.AppErrorCodes.Companion.DEFAULT_ERROR_CODE
import com.puj.stepsfitnessgame.data.network.ServerErrorResponseCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

class UserRemoteDataSourceImpl: UserRemoteDataSource {

    private val userService = ServiceFactory.create(UserApiService::class.java)

    override suspend fun loginUser(userCredentials: UserCredentials): Response<String> {
        try {
            val result = userService.loginUser(userCredentials)
            println(result.body())
            if(result.isSuccessful) {
                return Response.Success(result.body()!!)
            }
            return Response.Error(result.code())
        }
        catch (ex: Exception) {
            println(ex.printStackTrace())
            return Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit> {
        try {
            val result = userService.registerUser(userRegistrationInfo)

            if(result.isSuccessful) {
                return Response.Success(Unit)
            }

            return Response.Error(result.code())
        }
        catch (ex: Exception) {
            return Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun isUserLoggedIn(token: String): Response<Unit> {
        try {
            val result = userService.isUserLoggedIn(token)
            if(result.isSuccessful){
                return Response.Success(Unit)
            }
            if(result.code() == ServerErrorResponseCodes.UNAUTHORIZED){
                return Response.Error(UNAUTHORIZED_CODE)
            }
            return Response.Error(DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception){
            return Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun test() {
        try {
            println(userService.test().body())
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}