package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward
import com.puj.stepsfitnessgame.domain.models.guild.*

interface GuildRepository {

    fun getGuildList(): LiveData<List<GuildListItem>>

    fun getGuildData(): LiveData<GuildData>

    fun getGuildStatistics(): LiveData<GuildStatistics>

    fun getGuildParticipants(): LiveData<GuildParticipant>

    suspend fun createGuild(guildCreationInfo: GuildCreationInfo)

    suspend fun expelGuildParticipant(guildParticipantId: Long)

    suspend fun claimReward(): CompletedChallengeReward?

    suspend fun getIsOwner(): Boolean
}