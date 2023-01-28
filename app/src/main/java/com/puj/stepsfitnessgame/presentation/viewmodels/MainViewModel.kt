package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.UserRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.usecases.auth.GetIsUserLoggedInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val repository = UserRepositoryImpl(sharedPreferences)

    private val isUserLoggedInUseCase = GetIsUserLoggedInUseCase(repository)

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean>
        get() = _isUserLoggedIn

    fun isUserLoggedIn() {
        viewModelScope.launch(Dispatchers.Default) {
            when(isUserLoggedInUseCase()){
                is Response.Success<Unit> -> {
                    _isUserLoggedIn.postValue(true)
                }
                is Response.Error<Unit> -> {
                    _isUserLoggedIn.postValue(false)
                }
            }
        }
    }
}