package com.puj.stepsfitnessgame.domain.usecases.guild

import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class LeaveGuildUseCase(private val guildRepository: GuildRepository) {

    suspend operator fun invoke(): Boolean {
        return guildRepository.leaveGuild()
    }
}