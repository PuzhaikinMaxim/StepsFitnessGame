package com.puj.stepsfitnessgame.domain.usecases.guildenterrequest

import com.puj.stepsfitnessgame.domain.repositories.GuildEnterRequestRepository

class RefuseEnterUseCase(private val guildEnterRequestRepository: GuildEnterRequestRepository) {

    suspend operator fun invoke(userId: Long) {
        guildEnterRequestRepository.refuseEnter(userId)
    }
}