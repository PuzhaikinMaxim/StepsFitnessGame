package com.puj.stepsfitnessgame.domain.usecases.guild

import com.puj.stepsfitnessgame.domain.models.guild.GuildEditionInfo
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class CreateGuildUseCase(private val guildRepository: GuildRepository) {

    suspend operator fun invoke(guildEditionInfo: GuildEditionInfo) {
        guildRepository.createGuild(guildEditionInfo)
    }
}