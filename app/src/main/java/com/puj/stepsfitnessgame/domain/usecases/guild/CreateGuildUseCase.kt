package com.puj.stepsfitnessgame.domain.usecases.guild

import com.puj.stepsfitnessgame.domain.models.guild.GuildCreationInfo
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class CreateGuildUseCase(private val guildRepository: GuildRepository) {

    suspend operator fun invoke(guildCreationInfo: GuildCreationInfo) {
        guildRepository.createGuild(guildCreationInfo)
    }
}