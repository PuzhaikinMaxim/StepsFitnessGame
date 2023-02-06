package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.challenge.ChallengeRemoteDataSourceImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import com.puj.stepsfitnessgame.domain.repositories.ChallengeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeRepositoryImpl(
    sharedPreferences: SharedPreferences
    ): ChallengeRepository {

    private val token: String = sharedPreferences.getString(TOKEN_KEY, DEFAULT) ?: DEFAULT

    private val challengeRemoteDataSource = ChallengeRemoteDataSourceImpl(token)

    private val challengeList = MutableLiveData<List<Challenge>>()

    override fun getChallengesList(): LiveData<List<Challenge>> {
        val coroutine = CoroutineScope(Dispatchers.Default)
        coroutine.launch {
            setChallengesList()
        }

        return challengeList
    }

    override suspend fun startChallenge(challengeId: Int) {
        challengeRemoteDataSource.startChallenge(challengeId)
        setChallengesList()
    }

    override suspend fun cancelActiveChallenge() {
        challengeRemoteDataSource.cancelActiveChallenge()
        setChallengesList()
    }

    private suspend fun setChallengesList() {
        val response = challengeRemoteDataSource.getChallengesListByLevel(1)
        if(response is Response.Success){
            challengeList.postValue(response.data)
        }
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val DEFAULT = "default"
    }
}