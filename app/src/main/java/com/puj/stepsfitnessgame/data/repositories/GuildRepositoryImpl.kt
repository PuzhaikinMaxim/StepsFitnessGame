package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward
import com.puj.stepsfitnessgame.domain.models.guild.*
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class GuildRepositoryImpl(sharedPreferences: SharedPreferences): GuildRepository {

    override fun getGuildList(): LiveData<List<GuildListItem>> {
        TODO("Not yet implemented")
    }

    override fun getGuildData(): LiveData<GuildData> {
        TODO("Not yet implemented")
    }

    override fun getGuildStatistics(): LiveData<GuildStatistics> {
        TODO("Not yet implemented")
    }

    override fun getGuildParticipants(): LiveData<GuildParticipant> {
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

    override suspend fun getIsOwner(): Boolean {
        TODO("Not yet implemented")
    }
}