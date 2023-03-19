package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.CharacteristicsRepositoryImpl
import com.puj.stepsfitnessgame.data.repositories.UserDataRepositoryImpl
import com.puj.stepsfitnessgame.domain.usecases.characteristics.GetCharacteristicsUseCase
import com.puj.stepsfitnessgame.domain.usecases.characteristics.IncreaseEnduranceUseCase
import com.puj.stepsfitnessgame.domain.usecases.characteristics.IncreaseStrengthUseCase
import com.puj.stepsfitnessgame.domain.usecases.userdata.GetUserDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacteristicsViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val characteristicsRepository = CharacteristicsRepositoryImpl(sharedPreferences)

    private val getCharacteristicsUseCase = GetCharacteristicsUseCase(characteristicsRepository)

    private val increaseEnduranceUseCase = IncreaseEnduranceUseCase(characteristicsRepository)

    private val increaseStrengthUseCase = IncreaseStrengthUseCase(characteristicsRepository)

    private val userDataRepository = UserDataRepositoryImpl(sharedPreferences)

    private val getUserDataUseCase = GetUserDataUseCase(userDataRepository)

    private val _canPressButtons = MutableLiveData(true)

    val canPressButtons: LiveData<Boolean>
        get() = _canPressButtons

    val characteristics = getCharacteristicsUseCase()

    val userData = getUserDataUseCase()

    fun increaseEndurance() {
        _canPressButtons.value = false
        viewModelScope.launch(Dispatchers.Default) {
            increaseEnduranceUseCase()
            _canPressButtons.postValue(true)
        }
    }

    fun increaseStrength() {
        _canPressButtons.value = false
        viewModelScope.launch(Dispatchers.Default) {
            increaseStrengthUseCase()
            _canPressButtons.postValue(true)
        }
    }
}