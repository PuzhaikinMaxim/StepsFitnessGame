package com.puj.stepsfitnessgame.domain.usecases.guild

import com.puj.stepsfitnessgame.domain.models.guild.GuildEditionInfo
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class GetGuildEditionInfoUseCase(private val repository: GuildRepository) {

    suspend operator fun invoke(): GuildEditionInfo? {
        return repository.getGuildEditionInfo()
    }
}