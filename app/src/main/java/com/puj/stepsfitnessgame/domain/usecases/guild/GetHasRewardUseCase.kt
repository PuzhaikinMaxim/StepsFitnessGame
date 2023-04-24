package com.puj.stepsfitnessgame.domain.usecases.guild

import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class GetHasRewardUseCase(private val guildRepository: GuildRepository) {

    suspend operator fun invoke(): Boolean {
        return guildRepository.getHasReward()
    }
}