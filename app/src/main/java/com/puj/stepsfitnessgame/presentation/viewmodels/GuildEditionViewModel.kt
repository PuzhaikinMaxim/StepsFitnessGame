package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.domain.models.guild.GuildEditionInfo
import com.puj.stepsfitnessgame.domain.usecases.guild.EditGuildUseCase
import com.puj.stepsfitnessgame.domain.usecases.guild.GetGuildEditionInfoUseCase
import com.puj.stepsfitnessgame.presentation.InputValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuildEditionViewModel(
    sharedPreferences: SharedPreferences
): GuildEditorViewModel(sharedPreferences) {

    private val getGuildEditionInfoUseCase = GetGuildEditionInfoUseCase(repository)

    private val editGuildUseCase = EditGuildUseCase(repository)

    private val _guildEditionInfo = MutableLiveData<GuildEditionInfo>()
    val guildEditionInfo: LiveData<GuildEditionInfo>
        get() = _guildEditionInfo

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val guildEditionInfo = getGuildEditionInfoUseCase.invoke()
            if(guildEditionInfo != null)
                _guildEditionInfo.postValue(guildEditionInfo)
        }
    }

    fun editGuild(guildName: String) {
        val guildLogoId = getSelectedGuildLogoId() ?: return
        val guildEditionInfo = GuildEditionInfo(guildName, guildLogoId)
        if(!isGuildNameValid(guildName)) return
        viewModelScope.launch(Dispatchers.IO) {
            editGuildUseCase.invoke(guildEditionInfo)
            _shouldCloseScreen.postValue(Unit)
        }
    }

    private fun isGuildNameValid(guildName: String): Boolean {
        val validator = InputValidator(guildName)
        return validator.minSymbols(6).maxSymbols(30).validate().isValid
    }
}