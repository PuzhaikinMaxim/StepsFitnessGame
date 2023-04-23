package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.GuildRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.guild.GuildCreationInfo
import com.puj.stepsfitnessgame.domain.models.guild.GuildLogo
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository
import com.puj.stepsfitnessgame.domain.usecases.guild.CreateGuildUseCase
import com.puj.stepsfitnessgame.presentation.InputValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuildCreationViewModel(private val sharedPreferences: SharedPreferences): ViewModel() {

    private val guildRepository: GuildRepository = GuildRepositoryImpl(sharedPreferences)

    private val createGuildUseCase = CreateGuildUseCase(guildRepository)

    private val _guildLogoList = MutableLiveData<List<GuildLogo>>()
    val guildLogoList: LiveData<List<GuildLogo>>
        get() = _guildLogoList

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun creteGuild(guildName: String, guildLogoId: Int) {
        val guildCreationInfo = GuildCreationInfo(guildName, guildLogoId)
        if(!isGuildNameValid(guildName)) return
        viewModelScope.launch(Dispatchers.Default) {
            createGuildUseCase.invoke(guildCreationInfo)
            _shouldCloseScreen.postValue(Unit)
        }
    }

    fun createLogoList(guildLogoResourceIds: List<Int>) {
        val guildLogoList = ArrayList<GuildLogo>()
        for((counter, id) in guildLogoResourceIds.withIndex()){
            var guildLogo = GuildLogo(
                id,
                counter
            )
        }
        _guildLogoList.value = guildLogoList
    }

    private fun isGuildNameValid(guildName: String): Boolean {
        val validator = InputValidator(guildName)
        return validator.minSymbols(6).maxSymbols(30).validate().isValid
    }

}