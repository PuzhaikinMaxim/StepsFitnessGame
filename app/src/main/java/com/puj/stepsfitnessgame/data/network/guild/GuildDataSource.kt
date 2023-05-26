package com.puj.stepsfitnessgame.data.network.guild

import com.puj.stepsfitnessgame.data.network.challenge.CompletedChallengeRewardDto
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.guild.*

interface GuildDataSource {

    suspend fun getGuildList(): Response<List<GuildListItemDto>>

    suspend fun getGuildData(): Response<GuildData>

    suspend fun getGuildStatistics(): Response<GuildStatistics>

    suspend fun getGuildParticipants(): Response<List<GuildParticipant>>

    suspend fun createGuild(guildEditionInfo: GuildEditionInfo)

    suspend fun expelGuildParticipant(guildParticipantId: Long)

    suspend fun claimReward(): Response<CompletedChallengeRewardDto?>

    suspend fun getHasReward(): Response<Boolean>

    suspend fun getIsOwner(): Response<Boolean>

    suspend fun editGuild(guildEditionInfo: GuildEditionInfo)

    suspend fun getGuildEditionInfo(): Response<GuildEditionInfo>
}