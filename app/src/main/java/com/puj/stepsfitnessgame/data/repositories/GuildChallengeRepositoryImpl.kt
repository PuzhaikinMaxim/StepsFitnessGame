package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.CurrentGuildChallenge
import com.puj.stepsfitnessgame.domain.models.guild.GuildChallenge
import com.puj.stepsfitnessgame.domain.repositories.GuildChallengeRepository

class GuildChallengeRepositoryImpl(sharedPreferences: SharedPreferences): GuildChallengeRepository {

    override suspend fun selectGuildChallenge(guildChallengeId: Long) {
        TODO("Not yet implemented")
    }

    override fun getGuildChallenges(): LiveData<List<GuildChallenge>> {
        TODO("Not yet implemented")
    }

    override fun getCurrentGuildChallenge(): LiveData<CurrentGuildChallenge> {
        TODO("Not yet implemented")
    }
}