package com.puj.stepsfitnessgame.data.network.duel

import com.google.gson.annotations.SerializedName

data class DuelDto(
    val playerHp: Int,
    val playerInitialHp: Int,
    val opponentHp: Int,
    val opponentInitialHp: Int,
    val playerName: String?,
    val opponentName: String?,
    @SerializedName("duelFinished")
    val isDuelFinished: Boolean,
    @SerializedName("won")
    val isWon: Boolean,
    @SerializedName("canceled")
    val isCanceled: Boolean
) {
}