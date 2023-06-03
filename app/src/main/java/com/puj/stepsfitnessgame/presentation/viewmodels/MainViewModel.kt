package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.UserRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.authresult.AuthResult
import com.puj.stepsfitnessgame.domain.usecases.auth.GetIsUserLoggedInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val repository = UserRepositoryImpl(sharedPreferences)

    private val isUserLoggedInUseCase = GetIsUserLoggedInUseCase(repository)

    private val _isUserLoggedIn = MutableLiveData<AuthResult>()
    val isUserLoggedIn: LiveData<AuthResult>
        get() = _isUserLoggedIn

    fun isUserLoggedIn() {
        viewModelScope.launch(Dispatchers.Default) {
            val result = isUserLoggedInUseCase()
            _isUserLoggedIn.postValue(result)
        }
    }
}