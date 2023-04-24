package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.data.network.guildchallenge.GuildChallengeDataProviderImpl
import com.puj.stepsfitnessgame.domain.models.guild.CurrentGuildChallenge
import com.puj.stepsfitnessgame.domain.models.guild.GuildChallenge
import com.puj.stepsfitnessgame.domain.repositories.GuildChallengeRepository

class GuildChallengeRepositoryImpl(sharedPreferences: SharedPreferences): GuildChallengeRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val guildChallengeDataProvider = GuildChallengeDataProviderImpl(token)

    override suspend fun selectGuildChallenge(guildChallengeId: Long) {
        TODO("Not yet implemented")
    }

    override fun getGuildChallenges(): LiveData<List<GuildChallenge>> {
        TODO("Not yet implemented")
    }

    override fun getCurrentGuildChallenge(): LiveData<CurrentGuildChallenge?> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}