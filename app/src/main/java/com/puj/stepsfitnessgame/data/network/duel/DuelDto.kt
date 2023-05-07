package com.puj.stepsfitnessgame.data.network.duel

data class DuelDto(
    val player: PlayersDuelDto,
    val opponent: PlayersDuelDto,
    val isDuelFinished: Boolean,
    val isWon: Boolean,
    val isCanceled: Boolean
) {
}