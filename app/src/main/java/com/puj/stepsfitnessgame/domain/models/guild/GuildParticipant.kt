package com.puj.stepsfitnessgame.domain.models.guild

data class GuildParticipant(
    val participantId: Long,
    val participantName: String,
    val participantProfileImageId: Int,
    val participantLevel: Int
) {
}