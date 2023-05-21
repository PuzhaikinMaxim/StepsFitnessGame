package com.puj.stepsfitnessgame.domain.usecases.guild

import com.puj.stepsfitnessgame.domain.models.guild.GuildEditionInfo
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class EditGuildUseCase(private val guildRepository: GuildRepository) {

    suspend operator fun invoke(guildEditionInfo: GuildEditionInfo) {
        guildRepository.editGuild(guildEditionInfo)
    }
}