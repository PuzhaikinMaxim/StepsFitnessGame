package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.UserRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.usecases.LoginUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel(sharedPreferences: SharedPreferences): ViewModel(){

    private val repository = UserRepositoryImpl(sharedPreferences)

    private val loginUserUseCase = LoginUserUseCase(repository)

    val isAuthenticated = MutableLiveData<Unit>()

    val isAuthenticationError = MutableLiveData<Boolean>()

    init {
        isAuthenticationError.value = false
    }

    fun loginUser(username: String, password: String) {
        if(isCredentialsNotValid(username, password)){
            return;
        }

        val userCredentials = UserCredentials(username, password)
        viewModelScope.launch(Dispatchers.Default) {
            when(loginUserUseCase(userCredentials)) {
                is Response.Success -> {
                    isAuthenticated.value = Unit
                }
                is Response.Error -> {
                    showErrorWithDelay()
                }
            }
        }
    }

    private suspend fun showErrorWithDelay() {
        isAuthenticationError.value = true
        delay(ERROR_ON_SCREEN_DELAY)
        isAuthenticationError.value = false
    }

    private fun isCredentialsNotValid(username: String, password: String): Boolean {
        return true
    }

    companion object {
        private const val ERROR_ON_SCREEN_DELAY = 10L * 1000L

    }
}