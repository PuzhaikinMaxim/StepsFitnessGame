package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.userdata.UserData
import com.puj.stepsfitnessgame.domain.models.userdata.UserEditProfileData
import com.puj.stepsfitnessgame.domain.models.userdata.UserProfileData

interface UserDataRepository {

    fun getUserData(): LiveData<UserData>

    fun getUserProfileData(): LiveData<UserProfileData>

    suspend fun getUsername(): String

    suspend fun getUserEditProfileData(): UserEditProfileData?
}