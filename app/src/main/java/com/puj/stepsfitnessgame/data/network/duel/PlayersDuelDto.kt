package com.puj.stepsfitnessgame.data.network.duel

data class PlayersDuelDto(
    val name: String,
    val hp: Int,
    val initialHp: Int,
    val level: Int,
    val profileImageId: Int
) {
}