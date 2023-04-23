package com.puj.stepsfitnessgame.domain.usecases.guildchallenge

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.GuildChallenge
import com.puj.stepsfitnessgame.domain.repositories.GuildChallengeRepository

class GetGuildChallengesUseCase(private val guildChallengeRepository: GuildChallengeRepository) {

    operator fun invoke(): LiveData<List<GuildChallenge>> {
        return guildChallengeRepository.getGuildChallenges()
    }
}