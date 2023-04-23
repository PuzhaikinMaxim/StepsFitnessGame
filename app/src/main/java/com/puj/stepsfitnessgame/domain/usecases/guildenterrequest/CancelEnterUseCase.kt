package com.puj.stepsfitnessgame.domain.usecases.guildenterrequest

import com.puj.stepsfitnessgame.domain.repositories.GuildEnterRequestRepository

class CancelEnterUseCase(private val guildEnterRequestRepository: GuildEnterRequestRepository) {

    suspend operator fun invoke(guildId: Long) {
        guildEnterRequestRepository.cancelEnter(guildId)
    }
}