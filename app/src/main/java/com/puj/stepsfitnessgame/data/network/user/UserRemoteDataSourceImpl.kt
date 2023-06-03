package com.puj.stepsfitnessgame.data.network.user

import com.puj.stepsfitnessgame.data.network.AppErrorCodes.Companion.SERVER_NOT_RESPONDING_CODE
import com.puj.stepsfitnessgame.data.network.AppErrorCodes.Companion.UNAUTHORIZED_CODE
import com.puj.stepsfitnessgame.data.network.AppErrorCodes.Companion.DEFAULT_ERROR_CODE
import com.puj.stepsfitnessgame.data.network.ServerErrorResponseCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

class UserRemoteDataSourceImpl(private val token: String): UserRemoteDataSource {

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
            //println(ex.printStackTrace())
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
            ex.printStackTrace()
            return Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun changeUsername(username: String): Response<Boolean> {
        try {
            val result = userService.changeUsername(token, UsernameChangeRequest(username))
            if(result.isSuccessful){
                return Response.Success(true)
            }
            return Response.Error(DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception){
            return Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun setProfileImage(profileImageId: Int): Response<Boolean> {
        try {
            val result = userService.setPlayerProfileImage(token, profileImageId)
            if(result.isSuccessful){
                return Response.Success(result.body() ?: false)
            }
            return Response.Error(DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception){
            return Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun logOut() {
        try {
            userService.logOut(token)
        }
        catch (ex: Exception) {

        }
    }

    override suspend fun test() {
        try {
            println(userService.test().body())
        }
        catch (ex: Exception) {
            //ex.printStackTrace()
        }
    }
}