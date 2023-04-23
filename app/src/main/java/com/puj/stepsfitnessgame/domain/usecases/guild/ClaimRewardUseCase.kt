package com.puj.stepsfitnessgame.domain.usecases.guild

import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class ClaimRewardUseCase(private val guildRepository: GuildRepository) {

    suspend operator fun invoke(): CompletedChallengeReward? {
        return guildRepository.claimReward()
    }
}