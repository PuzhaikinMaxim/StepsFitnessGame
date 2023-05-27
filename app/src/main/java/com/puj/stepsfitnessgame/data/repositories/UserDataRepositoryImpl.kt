package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.userdata.UserDataRemoteDataSourceImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.userdata.UserData
import com.puj.stepsfitnessgame.domain.models.userdata.UserEditProfileData
import com.puj.stepsfitnessgame.domain.models.userdata.UserProfileData
import com.puj.stepsfitnessgame.domain.repositories.UserDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDataRepositoryImpl(
    sharedPreferences: SharedPreferences
): UserDataRepository {

    private val token: String = sharedPreferences.getString(TOKEN_KEY, DEFAULT) ?: DEFAULT

    private val userDataRemoteDataSource = UserDataRemoteDataSourceImpl(token)

    private val userData = MutableLiveData<UserData>()

    private val userProfileData = MutableLiveData<UserProfileData>()

    override fun getUserData(): LiveData<UserData> {
        val coroutineScope = CoroutineScope(Dispatchers.Default)

        coroutineScope.launch {
            val response = userDataRemoteDataSource.getUserLevel()

            if(response is Response.Success){
                userData.postValue(response.data)
            }
        }
        return userData
    }

    override fun getUserProfileData(): LiveData<UserProfileData> {
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {
            val response = userDataRemoteDataSource.getUserProfileData()

            if(response is Response.Success){
                userProfileData.postValue(response.data)
            }
        }
        return userProfileData
    }

    override suspend fun getUserEditProfileData(): UserEditProfileData? {
        val response = userDataRemoteDataSource.getEditProfileData()

        if(response is Response.Success){
            return response.data
        }

        return null
    }

    override suspend fun getUsername(): String {
        val response = userDataRemoteDataSource.getUserLevel()

        if(response is Response.Success){
            return response.data.username
        }
        return ""
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val DEFAULT = "default"
    }
}