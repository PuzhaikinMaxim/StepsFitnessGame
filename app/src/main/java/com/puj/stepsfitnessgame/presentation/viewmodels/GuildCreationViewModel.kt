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

    fun creteGuild(guildName: String) {
        val guildLogoId = getSelectedGuildLogoId() ?: return
        val guildCreationInfo = GuildCreationInfo(guildName, guildLogoId)
        if(!isGuildNameValid(guildName)) return
        viewModelScope.launch(Dispatchers.Default) {
            createGuildUseCase.invoke(guildCreationInfo)
            _shouldCloseScreen.postValue(Unit)
        }
    }

    private fun getSelectedGuildLogoId(): Int? {
        return _guildLogoList.value?.find { it.isSelected }?.guildLogoId
    }

    fun createLogoList(guildLogoResourceIds: List<Int>) {
        val guildLogoList = ArrayList<GuildLogo>()
        for((counter, id) in guildLogoResourceIds.withIndex()){
            val guildLogo = GuildLogo(
                id,
                counter,
                false
            )
            guildLogoList.add(guildLogo)
        }
        _guildLogoList.value = guildLogoList
    }

    fun selectGuildLogo(guildLogoId: Int) {
        val newGuildLogoList = mutableListOf<GuildLogo>()
        _guildLogoList.value?.forEach {
            newGuildLogoList.add(it.copy(isSelected = false))
        }
        newGuildLogoList[guildLogoId] = newGuildLogoList[guildLogoId].copy(isSelected = true)
        _guildLogoList.value = newGuildLogoList
    }

    private fun isGuildNameValid(guildName: String): Boolean {
        val validator = InputValidator(guildName)
        return validator.minSymbols(6).maxSymbols(30).validate().isValid
    }

}