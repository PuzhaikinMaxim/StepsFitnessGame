package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.guildchallenge.GuildChallengeDataSourceImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.guild.CurrentGuildChallenge
import com.puj.stepsfitnessgame.domain.models.guild.GuildChallenge
import com.puj.stepsfitnessgame.domain.repositories.GuildChallengeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuildChallengeRepositoryImpl(sharedPreferences: SharedPreferences): GuildChallengeRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val guildChallengeDataSource = GuildChallengeDataSourceImpl(token)

    private val currentGuildChallenge = MutableLiveData<CurrentGuildChallenge?>()

    private val guildChallenges = MutableLiveData<List<GuildChallenge>>()

    override suspend fun selectGuildChallenge(guildChallengeId: Long) {
        guildChallengeDataSource.selectGuildChallenge(guildChallengeId)
    }

    override fun getGuildChallenges(): LiveData<List<GuildChallenge>> {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = guildChallengeDataSource.getGuildChallenges()
            if(response is Response.Success){
                guildChallenges.postValue(response.data)
            }
        }
        return guildChallenges
    }

    override fun getCurrentGuildChallenge(): LiveData<CurrentGuildChallenge?> {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = guildChallengeDataSource.getCurrentGuildChallenge()
            if(response is Response.Success){
                currentGuildChallenge.postValue(response.data)
            }
            else{
                currentGuildChallenge.postValue(null)
            }
        }
        return currentGuildChallenge
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}