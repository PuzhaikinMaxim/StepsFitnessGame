package com.puj.stepsfitnessgame.domain.models.duel

import com.puj.stepsfitnessgame.domain.models.player.Player

data class DuelField(
    val playerHp: Int,
    val playerInitialHp: Int,
    val opponentHp: Int,
    val opponentInitialHp: Int,
    val player: Player,
    val opponent: Player,
    val isDuelFinished: Boolean = false,
    val isWon: Boolean = false,
    val isDuelCanceled: Boolean = false
) {
}