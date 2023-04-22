package com.puj.stepsfitnessgame.domain.models.guild

data class GuildStatistics(
    val amountOfCompletedChallenges: Int,
    val amountOfParticipants: Int,
    val participantsCollectiveLevel: Int
) {
}