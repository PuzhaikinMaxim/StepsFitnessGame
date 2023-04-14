package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.playerstatistics.PlayerStatisticsDataProviderImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics
import com.puj.stepsfitnessgame.domain.repositories.PlayerStatisticsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerStatisticsRepositoryImpl(
    private val sharedPreferences: SharedPreferences
    ) : PlayerStatisticsRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val playerStatisticsDataProvider = PlayerStatisticsDataProviderImpl(token)

    private val duelStatistics = MutableLiveData<DuelStatistics>()

    override fun getDuelStatistics(): LiveData<DuelStatistics> {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val response = playerStatisticsDataProvider.getDuelStatistics()
            if(response is Response.Success){
                duelStatistics.postValue(response.data)
            }
        }
        return duelStatistics
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}