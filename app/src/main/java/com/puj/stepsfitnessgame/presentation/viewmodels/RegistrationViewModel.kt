package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.UserRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.domain.models.user.UserRegistrationInfo
import com.puj.stepsfitnessgame.domain.usecases.auth.LoginUserUseCase
import com.puj.stepsfitnessgame.domain.usecases.auth.RegisterUserUseCase
import com.puj.stepsfitnessgame.presentation.InputValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(sharedPreferences: SharedPreferences) : ViewModel() {
    private val repository = UserRepositoryImpl(sharedPreferences)

    private val loginUserUseCase = LoginUserUseCase(repository)

    private val registerUserUseCase = RegisterUserUseCase(repository)

    private val _isRegistered = MutableLiveData<Unit>()
    val isRegistered: LiveData<Unit>
        get() = _isRegistered

    private val _isAuthenticated = MutableLiveData<Unit>()
    val isAuthenticated: LiveData<Unit>
        get() = _isAuthenticated

    private val _isRegistrationError = MutableLiveData<Unit>()
    val isRegistrationError: LiveData<Unit>
        get() = _isRegistrationError

    private val _isInputWrong = MutableLiveData<Unit>()
    val isInputWrong: LiveData<Unit>
        get() = _isInputWrong

    fun registerUser(username: String, email: String, password: String) {
        if (isCredentialsNotValid(username, email, password)){
            _isInputWrong.value = Unit
            return
        }

        val userCredentials = UserRegistrationInfo(username, email, password)
        viewModelScope.launch(Dispatchers.Default) {
            when(registerUserUseCase(userCredentials)) {
                is Response.Success -> {
                    _isRegistered.postValue(Unit)
                }
                is Response.Error -> {
                    _isRegistrationError.postValue(Unit)
                }
            }
        }
    }

    fun loginUser(username: String, password: String) {
        val userCredentials = UserCredentials(username, password)
        viewModelScope.launch(Dispatchers.Default) {
            val result = loginUserUseCase(userCredentials)
            if(result is Response.Success){
                _isAuthenticated.postValue(Unit)
            }
        }
    }

    private fun isCredentialsNotValid(username: String, email: String, password: String): Boolean {
        val usernameValidator = getUsernameValidator(username)
        val emailValidator = InputValidator(email).isEmail()
        val passwordValidator = getPasswordValidator(password)

        val isUsernameNotValid = !usernameValidator.validate().isValid
        val isEmailNotValid = !emailValidator.validate().isValid
        val isPasswordNotValid = !passwordValidator.validate().isValid

        return isUsernameNotValid || isEmailNotValid || isPasswordNotValid
    }

    private fun getUsernameValidator(username: String): InputValidator {
        val usernameValidator = InputValidator(username)
        return usernameValidator
            .isNotBlank()
            .minSymbols(6)
            .maxSymbols(30)
    }

    private fun getPasswordValidator(password: String): InputValidator {
        val passwordValidator = InputValidator(password)
        return passwordValidator
            .isNotBlank()
            .minSymbols(6)
            .maxSymbols(50)
            .hasLowerCase()
            .hasUpperCase()
            .hasNumbers()
    }
}