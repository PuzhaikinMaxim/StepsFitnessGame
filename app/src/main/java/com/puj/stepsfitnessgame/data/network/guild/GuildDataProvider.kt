package com.puj.stepsfitnessgame.data.network.guild

import com.puj.stepsfitnessgame.data.network.challenge.CompletedChallengeRewardDto
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward
import com.puj.stepsfitnessgame.domain.models.guild.*

interface GuildDataProvider {

    suspend fun getGuildList(): Response<List<GuildListItemDto>>

    suspend fun getGuildData(): Response<GuildData>

    suspend fun getGuildStatistics(): Response<GuildStatistics>

    suspend fun getGuildParticipants(): Response<List<GuildParticipant>>

    suspend fun createGuild(guildCreationInfo: GuildCreationInfo)

    suspend fun expelGuildParticipant(guildParticipantId: Long)

    suspend fun claimReward(): Response<CompletedChallengeRewardDto?>

    suspend fun getHasReward(): Response<Boolean>

    suspend fun getIsOwner(): Response<Boolean>
}