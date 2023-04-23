package com.puj.stepsfitnessgame.domain.usecases.guildchallenge

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.CurrentGuildChallenge
import com.puj.stepsfitnessgame.domain.repositories.GuildChallengeRepository

class GetCurrentGuildChallengeUseCase(
    private val guildChallengeRepository: GuildChallengeRepository
    ) {

    operator fun invoke(): LiveData<CurrentGuildChallenge> {
        return guildChallengeRepository.getCurrentGuildChallenge()
    }
}