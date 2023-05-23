package com.puj.stepsfitnessgame.data.network.duel

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response

class DuelRemoteDataSourceImpl(
    private val token: String
): DuelRemoteDataSource {

    private var duelStompClient: DuelStompClient? = null

    private val duelApiService = ServiceFactory.create(DuelApiService::class.java)

    fun startChallenge() {

    }

    override fun startDuelSearch(username: String) {
        duelStompClient!!.initializeConnection(username)
    }

    override fun getIsOpponentFound(): LiveData<Boolean> {
        duelStompClient = DuelStompClient(token)
        return duelStompClient!!.isOpponentFound
    }

    override fun tryFindGame() {
        if(duelStompClient != null){
            duelStompClient!!.tryFindGame()
        }
    }

    override fun stopDuelSearch() {
        duelStompClient?.stopDuelSearch()
    }

    override suspend fun updateStepAmount(amountOfSteps: Int): Boolean {
        return try {
            duelApiService.updateProgress(token, amountOfSteps.toString())
            true
        } catch (ex: Exception){
            false
        }
    }

    override suspend fun getDuelField(): Response<DuelDto> {
        try {
            val response = duelApiService.getDuel(token)
            if(response.isSuccessful && response.body() != null){
                return Response.Success(response.body()!!)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception) {
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun claimReward(): Response<FinishedDuelRewardDto> {
        try {
            val response = duelApiService.claimReward(token)
            if(response.isSuccessful && response.body() != null){
                return Response.Success(response.body()!!)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception) {
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun cancelDuel(): Response<Boolean> {
        try {
            val response = duelApiService.cancelDuel(token)
            if(response.isSuccessful && response.body() != null){
                return Response.Success(response.body()!!)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception) {
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }
}