package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.stepsfitnessgame.data.network.challenge.CompletedChallengeRewardMapper
import com.puj.stepsfitnessgame.data.network.guild.GuildDataProviderImpl
import com.puj.stepsfitnessgame.data.network.guild.GuildListItemDto
import com.puj.stepsfitnessgame.data.network.guild.GuildMapper
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward
import com.puj.stepsfitnessgame.domain.models.guild.*
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuildRepositoryImpl(sharedPreferences: SharedPreferences): GuildRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val guildMapper = GuildMapper()

    private val completedChallengeRewardMapper = CompletedChallengeRewardMapper()

    private val guildRemoteDataSource = GuildDataProviderImpl(token)

    private val guildList = MutableLiveData<List<GuildListItemDto>>()

    private val guildData = MutableLiveData<GuildData>()

    private val guildStatistics = MutableLiveData<GuildStatistics>()

    private val guildParticipants = MutableLiveData<List<GuildParticipant>>()

    override fun getGuildList(): LiveData<List<GuildListItem>> {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = guildRemoteDataSource.getGuildList()
            if(response is Response.Success){
                guildList.postValue(response.data)
            }
        }
        return Transformations.map(guildList){
            guildMapper.mapGuildListDtoToGuildList(it)
        }
    }

    override fun getGuildData(): LiveData<GuildData> {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = guildRemoteDataSource.getGuildData()
            if(response is Response.Success){
                guildData.postValue(response.data)
            }
        }
        return guildData
    }

    override fun getGuildStatistics(): LiveData<GuildStatistics> {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = guildRemoteDataSource.getGuildStatistics()
            if(response is Response.Success){
                guildStatistics.postValue(response.data)
            }
        }
        return guildStatistics
    }

    override fun getGuildParticipants(): LiveData<List<GuildParticipant>> {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = guildRemoteDataSource.getGuildParticipants()
            if(response is Response.Success){
                guildParticipants.postValue(response.data)
            }
        }
        return guildParticipants
    }

    override suspend fun createGuild(guildCreationInfo: GuildCreationInfo) {
        guildRemoteDataSource.createGuild(guildCreationInfo)
    }

    override suspend fun expelGuildParticipant(guildParticipantId: Long) {
        guildRemoteDataSource.expelGuildParticipant(guildParticipantId)
    }

    override suspend fun claimReward(): CompletedChallengeReward {
        val response = guildRemoteDataSource.claimReward()
        if(response is Response.Success){
            val completedChallengeRewardDto = response.data ?: throw RuntimeException("Reward is null")
            return completedChallengeRewardMapper.mapCompletedChallengeDataDtoToCompletedChallengeData(
                completedChallengeRewardDto
            )
        }
        throw RuntimeException("Reward is null");
    }

    override suspend fun getHasReward(): Boolean {
        val response = guildRemoteDataSource.getHasReward()
        if(response is Response.Success){
            return response.data
        }
        return false
    }

    override suspend fun getIsOwner(): Boolean {
        val response = guildRemoteDataSource.getIsOwner()
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