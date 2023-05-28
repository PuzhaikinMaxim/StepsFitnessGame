package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.UserDataRepositoryImpl
import com.puj.stepsfitnessgame.data.repositories.UserRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.UserRepository
import com.puj.stepsfitnessgame.domain.usecases.user.LogOutUseCase
import com.puj.stepsfitnessgame.domain.usecases.userdata.GetUserDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainMenuViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val userDataRepository = UserDataRepositoryImpl(sharedPreferences)

    private val userRepository = UserRepositoryImpl(sharedPreferences)

    private val logOutUseCase = LogOutUseCase(userRepository)

    private val getUserDataUseCase = GetUserDataUseCase(userDataRepository)

    val userData = getUserDataUseCase()

    private val _isUserLoggedOut = MutableLiveData<Unit>()
    val isUserLoggedOut: LiveData<Unit>
        get() = _isUserLoggedOut

    init {
        val str = sharedPreferences.getString("authToken", "DEFAULT")
        println(str)
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            logOutUseCase()
            _isUserLoggedOut.postValue(Unit)
        }
    }
}