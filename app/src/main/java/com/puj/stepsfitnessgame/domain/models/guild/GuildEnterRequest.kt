package com.puj.stepsfitnessgame.domain.models.guild

data class GuildEnterRequest(
    val userId: Long,
    val userName: String,
    val userLevel: Int
) {
}