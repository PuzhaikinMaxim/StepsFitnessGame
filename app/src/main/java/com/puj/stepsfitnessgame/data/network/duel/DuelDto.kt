package com.puj.stepsfitnessgame.data.network.duel

import com.google.gson.annotations.SerializedName

data class DuelDto(
    val player: PlayersDuelDto,
    val opponent: PlayersDuelDto,
    @SerializedName("duelFinished")
    val isDuelFinished: Boolean,
    @SerializedName("won")
    val isWon: Boolean,
    @SerializedName("canceled")
    val isCanceled: Boolean
) {
}