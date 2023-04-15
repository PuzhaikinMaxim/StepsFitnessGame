package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.stepsfitnessgame.data.network.duel.*
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics
import com.puj.stepsfitnessgame.domain.models.duel.FinishedDuelReward
import com.puj.stepsfitnessgame.domain.repositories.DuelRepository
import com.puj.stepsfitnessgame.domain.repositories.UserDataRepository
import kotlinx.coroutines.*

class DuelRepositoryImpl(
    private val sharedPreferences: SharedPreferences
    ) : DuelRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val userDataRepository: UserDataRepository = UserDataRepositoryImpl(sharedPreferences)

    private val finishedDuelRewardMapper: FinishedDuelRewardMapper = FinishedDuelRewardMapper()

    private val duelMapper = DuelMapper()

    private val duelStatistics = MutableLiveData<DuelStatistics>()

    private val duelField = MutableLiveData<DuelDto>()

    private val duelRemoteDataSourceImpl = DuelRemoteDataSourceImpl(token)

    private var searchCoroutine: Job? = null

    override fun startDuelSearch() {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        searchCoroutine = coroutineScope.launch {
            val username = userDataRepository.getUsername()
            duelRemoteDataSourceImpl.startDuelSearch(username)
            for(i in 0..360){
                duelRemoteDataSourceImpl.tryFindGame()
                delay(1000)
            }
        }
    }

    override fun stopDuelSearch() {
        searchCoroutine?.cancel()
        duelRemoteDataSourceImpl.stopDuelSearch()
    }

    override fun getDuelField(): LiveData<DuelField> {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
            val response = duelRemoteDataSourceImpl.getDuelField()
            if(response is Response.Success){
                duelField.postValue(response.data)
            }
        }
        return Transformations.map(duelField) {
            duelMapper.mapDuelDtoToDuelField(it)
        }
    }

    override fun getIsOpponentFound(): LiveData<Boolean> {
        return duelRemoteDataSourceImpl.getIsOpponentFound()
    }

    override fun getDuelStatistics(): LiveData<DuelStatistics> {
        return duelStatistics
    }

    override suspend fun claimReward(): FinishedDuelReward? {
        val response = duelRemoteDataSourceImpl.claimReward()
        if(response is Response.Success){
            return finishedDuelRewardMapper.mapFinishedDuelRewardDtoToFinishedDuelReward(
                response.data
            )
        }
        return null
    }

    override suspend fun cancelDuel(): Boolean {
        val response = duelRemoteDataSourceImpl.cancelDuel()
        if(response is Response.Success){
            return response.data
        }
        return false
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}