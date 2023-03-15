package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.challenge.ChallengeRemoteDataSourceImpl
import com.puj.stepsfitnessgame.data.network.challenge.FakeChallengeRemoteDataSource
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge
import com.puj.stepsfitnessgame.domain.repositories.ChallengeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeRepositoryImpl(
    sharedPreferences: SharedPreferences
    ): ChallengeRepository {

    private val token: String = sharedPreferences.getString(TOKEN_KEY, TOKEN_DEFAULT) ?: TOKEN_DEFAULT

    private val level: Int = sharedPreferences.getInt(LEVEL_KEY, LEVEL_DEFAULT)

    private val challengeRemoteDataSource = ChallengeRemoteDataSourceImpl(token)

    private val challengeList = MutableLiveData<List<Challenge>>()

    override fun getChallengesList(): MutableLiveData<List<Challenge>> {
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

    override suspend fun endActiveChallenge() {
        TODO("Not yet implemented")
    }

    private suspend fun setChallengesList() {
        val response = challengeRemoteDataSource.getChallengesListByLevel(level)
        if(response is Response.Success){
            val newChallengeList = ArrayList(response.data)

            val activeChallenge = challengeRemoteDataSource.getActiveChallenge()
            if(activeChallenge != null){
                newChallengeList.add(activeChallenge)
            }

            newChallengeList.sortByDescending { it.isStarted }

            challengeList.postValue(newChallengeList)
        }
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"

        private const val LEVEL_KEY = "selectedLevel"
        private const val LEVEL_DEFAULT = 1
    }
}