package com.puj.stepsfitnessgame.domain.usecases.guildchallenge

import com.puj.stepsfitnessgame.domain.repositories.GuildChallengeRepository

class SelectGuildChallengeUseCase(private val guildChallengeRepository: GuildChallengeRepository) {

    suspend operator fun invoke(guildChallengeId: Long) {
        guildChallengeRepository.selectGuildChallenge(guildChallengeId)
    }
}