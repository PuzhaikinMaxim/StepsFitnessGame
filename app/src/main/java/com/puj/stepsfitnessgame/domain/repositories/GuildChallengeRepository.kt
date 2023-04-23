package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.CurrentGuildChallenge
import com.puj.stepsfitnessgame.domain.models.guild.GuildChallenge

interface GuildChallengeRepository {

    suspend fun selectGuildChallenge(guildChallengeId: Long)

    fun getGuildChallenges(): LiveData<List<GuildChallenge>>

    fun getCurrentGuildChallenge(): LiveData<CurrentGuildChallenge>
}