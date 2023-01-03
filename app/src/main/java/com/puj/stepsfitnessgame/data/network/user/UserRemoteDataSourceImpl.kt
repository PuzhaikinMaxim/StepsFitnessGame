package com.puj.stepsfitnessgame.data.network.user

import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

class UserRemoteDataSourceImpl: UserRemoteDataSource {

    private val userService = ServiceFactory.create(UserApiService::class.java)

    override fun loginUser(userCredentials: UserCredentials): Response<String> {
        try {
            val result = userService.loginUser(userCredentials).execute()
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

    override fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit> {
        try {
            val result = userService.registerUser(userRegistrationInfo).execute()
            if(result.isSuccessful) {
                return Response.Success(Unit)
            }
            return Response.Error(result.code())
        }
        catch (ex: Exception) {
            return Response.Error(SERVER_NOT_RESPONDING_CODE)
        }
    }

    override fun test() {
        try {
            println(userService.test().execute().body())
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    companion object {
        private const val SERVER_NOT_RESPONDING_CODE = 1
    }
}