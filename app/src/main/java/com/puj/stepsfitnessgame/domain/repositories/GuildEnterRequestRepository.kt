package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest

interface GuildEnterRequestRepository {

    suspend fun requestEnter(guildId: Long)

    suspend fun cancelEnter(guildId: Long)

    fun getGuildEnterRequests(): LiveData<List<GuildEnterRequest>>

    suspend fun refuseEnter(userId: Long)

    suspend fun acceptEnter(userId: Long)
}