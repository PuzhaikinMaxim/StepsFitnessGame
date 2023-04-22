package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest

interface GuildEnterRequestRepository {

    fun requestEnter(guildId: Long)

    fun getGuildEnterRequests(): LiveData<List<GuildEnterRequest>>

    fun refuseEnter(userId: Long)

    fun acceptEnter(userId: Long)
}