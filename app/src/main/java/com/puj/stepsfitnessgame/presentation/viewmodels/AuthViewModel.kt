package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.UserRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.usecases.auth.LoginUserUseCase
import com.puj.stepsfitnessgame.presentation.InputValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(sharedPreferences: SharedPreferences): ViewModel(){

    private val repository = UserRepositoryImpl(sharedPreferences)

    private val loginUserUseCase = LoginUserUseCase(repository)

    private val _isAuthenticated = MutableLiveData<Unit>()
    val isAuthenticated: LiveData<Unit>
        get() = _isAuthenticated

    private val _isAuthenticationError = MutableLiveData<Unit>()
    val isAuthenticationError: LiveData<Unit>
        get() = _isAuthenticationError

    private val _isInputWrong = MutableLiveData<Unit>()
    val isInputWrong: LiveData<Unit>
        get() = _isInputWrong

    fun loginUser(username: String, password: String) {
        if (isCredentialsNotValid(username, password)){
            _isInputWrong.value = Unit
            return
        }

        val userCredentials = UserCredentials(username, password)
        viewModelScope.launch(Dispatchers.Default) {
            when(loginUserUseCase(userCredentials)) {
                is Response.Success -> {
                    _isAuthenticated.postValue(Unit)
                }
                is Response.Error -> {
                    _isAuthenticationError.postValue(Unit)
                }
            }
        }
    }

    private fun isCredentialsNotValid(username: String, password: String): Boolean {
        val usernameValidator = getUsernameValidator(username)
        val passwordValidator = getPasswordValidator(password)

        val isUsernameNotValid = !usernameValidator.validate().isValid
        val isPasswordNotValid = !passwordValidator.validate().isValid
        return isUsernameNotValid || isPasswordNotValid
    }

    private fun getUsernameValidator(username: String): InputValidator {
        val usernameValidator = InputValidator(username)
        return usernameValidator
            .isNotBlank()
    }

    private fun getPasswordValidator(password: String): InputValidator {
        val passwordValidator = InputValidator(password)
        return passwordValidator
            .isNotBlank()
    }
}