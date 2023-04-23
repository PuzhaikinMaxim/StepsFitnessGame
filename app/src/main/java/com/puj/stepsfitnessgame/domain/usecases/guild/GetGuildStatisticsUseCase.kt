package com.puj.stepsfitnessgame.domain.usecases.guild

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.GuildStatistics
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class GetGuildStatisticsUseCase(private val guildRepository: GuildRepository) {

    operator fun invoke(): LiveData<GuildStatistics> {
        return guildRepository.getGuildStatistics()
    }
}