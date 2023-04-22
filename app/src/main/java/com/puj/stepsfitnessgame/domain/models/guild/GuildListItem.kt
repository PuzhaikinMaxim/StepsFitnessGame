package com.puj.stepsfitnessgame.domain.models.guild

data class GuildListItem(
    val guildId: Long,
    val guildName: String,
    val rank: Int,
    val amountOfPlayers: Int,
    val isEnterRequested: Boolean
) {
}