package com.puj.stepsfitnessgame.data.network.guildenterrequest

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest

interface GuildEnterRequestDataProvider {

    suspend fun requestEnter(guildId: Long)

    suspend fun cancelEnter(guildId: Long)

    suspend fun getGuildEnterRequests(): Response<List<GuildEnterRequest>>

    suspend fun refuseEnter(requestId: Long)

    suspend fun acceptEnter(requestId: Long)
}