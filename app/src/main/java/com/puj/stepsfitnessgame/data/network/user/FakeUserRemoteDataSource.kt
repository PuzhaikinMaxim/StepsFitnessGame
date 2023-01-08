package com.puj.stepsfitnessgame.data.network.user

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

class FakeUserRemoteDataSource: UserRemoteDataSource {

    private val testUser =
        UserCredentials(username = "Test12345678", password = "Password1")

    override fun loginUser(userCredentials: UserCredentials): Response<String> {
        return if(userCredentials == testUser) {
            Response.Success("testCred")
        } else {
            Response.Error(401)
        }
    }

    override fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit> {
        if(userRegistrationInfo.username == testUser.username){
            return Response.Error(403)
        }
        return Response.Success(Unit)
    }

    override fun isUserLoggedIn(token: String): Response<Unit> {
        return Response.Success(Unit)
    }

    override fun test() {
        TODO("Not yet implemented")
    }
}