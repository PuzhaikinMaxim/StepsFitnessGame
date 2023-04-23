package com.puj.stepsfitnessgame.domain.usecases.guild

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.GuildParticipant
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class GetGuildParticipantsUseCase(private val guildRepository: GuildRepository) {

    operator fun invoke(): LiveData<GuildParticipant> {
        return guildRepository.getGuildParticipants()
    }
}