package com.puj.stepsfitnessgame.domain.usecases.guildenterrequest

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest
import com.puj.stepsfitnessgame.domain.repositories.GuildEnterRequestRepository

class GetGuildEnterRequestsUseCase(
    private val guildEnterRequestRepository: GuildEnterRequestRepository
    ) {

    operator fun invoke(): LiveData<List<GuildEnterRequest>> {
        return guildEnterRequestRepository.getGuildEnterRequests()
    }
}