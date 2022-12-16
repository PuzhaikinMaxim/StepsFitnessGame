package com.puj.stepsfitnessgame.data

import android.content.SharedPreferences
import com.puj.stepsfitnessgame.data.network.UserRemoteDataSource
import com.puj.stepsfitnessgame.domain.UserRepository
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo

class UserRepositoryImpl(private val sharedPreferences: SharedPreferences) : UserRepository {

    private val userRemoteDataSource = UserRemoteDataSource()

    override fun registerUser(userRegistrationInfo: UserRegistrationInfo): Response<Unit> {
        return userRemoteDataSource.registerUser(userRegistrationInfo)
    }

    override fun loginUser(userCredentials: UserCredentials): Response<Unit> {
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
            putString(key, value)
        }
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
    }
}