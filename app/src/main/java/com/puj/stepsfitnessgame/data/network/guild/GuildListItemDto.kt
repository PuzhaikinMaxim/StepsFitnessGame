package com.puj.stepsfitnessgame.data.network.guild

import com.google.gson.annotations.SerializedName

data class GuildListItemDto(
    val guildId: Long,
    val guildName: String,
    val rank: Int,
    val amountOfPlayers: Int,
    @SerializedName("enterRequested")
    val isEnterRequested: Boolean
) {
}