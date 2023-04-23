package com.puj.stepsfitnessgame.domain.usecases.guild

import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class GetIsOwnerUseCase(private val guildRepository: GuildRepository) {

    suspend operator fun invoke(): Boolean {
        return guildRepository.getIsOwner()
    }
}