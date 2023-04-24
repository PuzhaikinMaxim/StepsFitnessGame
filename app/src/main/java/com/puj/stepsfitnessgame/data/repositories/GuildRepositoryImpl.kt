package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.data.network.guild.GuildDataProviderImpl
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward
import com.puj.stepsfitnessgame.domain.models.guild.*
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class GuildRepositoryImpl(sharedPreferences: SharedPreferences): GuildRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val guildRemoteDataSource = GuildDataProviderImpl(token)

    override fun getGuildList(): LiveData<List<GuildListItem>> {
        TODO("Not yet implemented")
    }

    override fun getGuildData(): LiveData<GuildData> {
        TODO("Not yet implemented")
    }

    override fun getGuildStatistics(): LiveData<GuildStatistics> {
        TODO("Not yet implemented")
    }

    override fun getGuildParticipants(): LiveData<List<GuildParticipant>> {
        TODO("Not yet implemented")
    }

    override suspend fun createGuild(guildCreationInfo: GuildCreationInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun expelGuildParticipant(guildParticipantId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun claimReward(): CompletedChallengeReward {
        TODO("Not yet implemented")
    }

    override suspend fun getHasReward(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getIsOwner(): Boolean {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}