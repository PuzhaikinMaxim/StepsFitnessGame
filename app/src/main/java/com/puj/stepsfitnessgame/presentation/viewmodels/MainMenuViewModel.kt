package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.UserDataRepositoryImpl
import com.puj.stepsfitnessgame.domain.usecases.userdata.GetUserDataUseCase

class MainMenuViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val userDataRepository = UserDataRepositoryImpl(sharedPreferences)

    private val getUserDataUseCase = GetUserDataUseCase(userDataRepository)

    val userData = getUserDataUseCase()

    init {
        val str = sharedPreferences.getString("authToken", "DEFAULT")
        println(str)
    }
}