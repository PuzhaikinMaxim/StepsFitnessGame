package com.puj.stepsfitnessgame.data.network.guildchallenge

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.guild.CurrentGuildChallenge
import com.puj.stepsfitnessgame.domain.models.guild.GuildChallenge

interface GuildChallengeDataProvider {

    suspend fun selectGuildChallenge(guildChallengeId: Long)

    suspend fun getGuildChallenges(): Response<List<GuildChallenge>>

    suspend fun getCurrentGuildChallenge(): Response<CurrentGuildChallenge?>

    suspend fun updateProgress(stepCount: Int)
}