package com.puj.stepsfitnessgame.domain.models.challenge

data class Challenge(
    val challengeName: String,
    val score: Int,
    val goal: Int,
    val stepsTaken: Int,
    val timeTillEnd: String
) {
}