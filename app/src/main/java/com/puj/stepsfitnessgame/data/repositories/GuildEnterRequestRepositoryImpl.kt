package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.data.network.guildenterrequest.GuildEnterRequestDataProviderImpl
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest
import com.puj.stepsfitnessgame.domain.repositories.GuildEnterRequestRepository

class GuildEnterRequestRepositoryImpl(
    sharedPreferences: SharedPreferences
): GuildEnterRequestRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val guildEnterRequestDataProvider = GuildEnterRequestDataProviderImpl(token)

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

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}