package com.puj.stepsfitnessgame.data.network

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

class UserRemoteDataSource {

    private val userService = ServiceFactory.create(UserApiService::class.java)

    fun loginUser(userCredentials: UserCredentials): Response<String> {
        val result = userService.loginUser(userCredentials).execute()
        if(result.isSuccessful) {
            return Response.Success(result.body()!!)
        }
        return Response.Error(result.code())
    }

    fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit> {
        val result = userService.registerUser(userRegistrationInfo).execute()
        if(result.isSuccessful) {
            return Response.Success(Unit)
        }
        return Response.Error(result.code())
    }

    fun test() {
        println(userService.test().execute().body())
    }
}