package com.puj.stepsfitnessgame.domain.usecases.guild

import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class ExpelGuildParticipantUseCase(private val guildRepository: GuildRepository) {

    suspend operator fun invoke(participantId: Long) {
        guildRepository.expelGuildParticipant(participantId)
    }
}