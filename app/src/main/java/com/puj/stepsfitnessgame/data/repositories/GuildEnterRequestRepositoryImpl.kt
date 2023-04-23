package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest
import com.puj.stepsfitnessgame.domain.repositories.GuildEnterRequestRepository

class GuildEnterRequestRepositoryImpl(
    sharedPreferences: SharedPreferences
): GuildEnterRequestRepository {

    override suspend fun requestEnter(guildId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun cancelEnter(guildId: Long) {
        TODO("Not yet implemented")
    }

    override fun getGuildEnterRequests(): LiveData<List<GuildEnterRequest>> {
        TODO("Not yet implemented")
    }

    override suspend fun refuseEnter(userId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun acceptEnter(userId: Long) {
        TODO("Not yet implemented")
    }
}