package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.GuildRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.guild.GuildLogo
import com.puj.stepsfitnessgame.presentation.InputValidator

open class GuildEditorViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    protected val repository = GuildRepositoryImpl(sharedPreferences)

    protected val _guildLogoList = MutableLiveData<List<GuildLogo>>()
    val guildLogoList: LiveData<List<GuildLogo>>
        get() = _guildLogoList


    protected fun getSelectedGuildLogoId(): Int? {
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