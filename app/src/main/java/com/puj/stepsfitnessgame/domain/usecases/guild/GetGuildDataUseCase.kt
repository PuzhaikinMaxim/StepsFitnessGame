package com.puj.stepsfitnessgame.domain.usecases.guild

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.GuildData
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class GetGuildDataUseCase(private val guildRepository: GuildRepository) {

    operator fun invoke(): LiveData<GuildData> {
        return guildRepository.getGuildData()
    }
}