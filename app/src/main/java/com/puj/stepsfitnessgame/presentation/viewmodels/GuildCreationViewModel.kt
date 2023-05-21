package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.GuildRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.guild.GuildEditionInfo
import com.puj.stepsfitnessgame.domain.models.guild.GuildLogo
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository
import com.puj.stepsfitnessgame.domain.usecases.guild.CreateGuildUseCase
import com.puj.stepsfitnessgame.presentation.InputValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuildCreationViewModel(
    private val sharedPreferences: SharedPreferences
    ): GuildEditorViewModel(sharedPreferences) {

    private val guildRepository: GuildRepository = GuildRepositoryImpl(sharedPreferences)

    private val createGuildUseCase = CreateGuildUseCase(guildRepository)

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun creteGuild(guildName: String) {
        val guildLogoId = getSelectedGuildLogoId() ?: return
        val guildEditionInfo = GuildEditionInfo(guildName, guildLogoId)
        if(!isGuildNameValid(guildName)) return
        viewModelScope.launch(Dispatchers.Default) {
            createGuildUseCase.invoke(guildEditionInfo)
            _shouldCloseScreen.postValue(Unit)
        }
    }

    private fun isGuildNameValid(guildName: String): Boolean {
        val validator = InputValidator(guildName)
        return validator.minSymbols(6).maxSymbols(30).validate().isValid
    }

}