package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository
import com.puj.stepsfitnessgame.domain.repositories.UserDataRepository

class DuelRepositoryImpl(
    private val sharedPreferences: SharedPreferences
    ) : DuelRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val userDataRepository: UserDataRepository = UserDataRepositoryImpl(sharedPreferences)

    private val duelStatistics = MutableLiveData<DuelStatistics>()

    private val duelField = MutableLiveData<DuelField>()

    private val username = userDataRepository.getUserData().value?.username ?: ""

    override fun startDuelSearch(): LiveData<Boolean> {
        TODO("Not yet implemented")
    }

    override fun stopDuelSearch() {
        TODO("Not yet implemented")
    }

    override fun getDuelField(): LiveData<DuelField> {
        return duelField
    }

    override fun getDuelStatistics(): LiveData<DuelStatistics> {
        return duelStatistics
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}