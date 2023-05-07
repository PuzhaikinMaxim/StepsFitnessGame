package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.UserDataRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.UserDataRepository
import com.puj.stepsfitnessgame.domain.usecases.userdata.GetUserProfileDataUseCase

class UserProfileViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val repository: UserDataRepository = UserDataRepositoryImpl(sharedPreferences)

    private val getUSerProfileDataUseCase = GetUserProfileDataUseCase(repository)

    val userProfileData = getUSerProfileDataUseCase()
}