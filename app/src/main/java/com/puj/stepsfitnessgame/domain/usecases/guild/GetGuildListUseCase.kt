package com.puj.stepsfitnessgame.domain.usecases.guild

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.GuildListItem
import com.puj.stepsfitnessgame.domain.repositories.GuildRepository

class GetGuildListUseCase(private val guildRepository: GuildRepository) {

    operator fun invoke(): LiveData<List<GuildListItem>> {
        return guildRepository.getGuildList()
    }
}