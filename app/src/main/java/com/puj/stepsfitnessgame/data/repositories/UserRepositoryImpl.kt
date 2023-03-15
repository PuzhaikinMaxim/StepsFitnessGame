package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import com.puj.stepsfitnessgame.data.network.user.FakeUserRemoteDataSource
import com.puj.stepsfitnessgame.data.network.user.UserRemoteDataSourceImpl
import com.puj.stepsfitnessgame.domain.repositories.UserRepository
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

class UserRepositoryImpl(private val sharedPreferences: SharedPreferences) : UserRepository {

    private val userRemoteDataSource = UserRemoteDataSourceImpl()

    override suspend fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit> {
        return userRemoteDataSource.registerUser(userRegistrationInfo)
    }

    override suspend fun loginUser(userCredentials: UserCredentials): Response<Unit> {
        return when (val response = userRemoteDataSource.loginUser(userCredentials)) {
            is Response.Success -> {
                saveToSharedPreferences(TOKEN_KEY, response.data)
                Response.Success(Unit)
            }
            is Response.Error -> {
                Response.Error(response.errorCode)
            }
        }
    }

    private fun saveToSharedPreferences(key: String, value: String) {
        with(sharedPreferences.edit()){
            println("value: $value")
            putString(key, value)
            apply()
        }
    }

    override suspend fun isUserLoggedIn(): Response<Unit> {
        val token = sharedPreferences.getString(TOKEN_KEY, DEFAULT) ?: DEFAULT
        println("token $token")
        return userRemoteDataSource.isUserLoggedIn(token)
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val DEFAULT = "default"
    }
}