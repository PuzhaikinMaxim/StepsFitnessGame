package com.puj.stepsfitnessgame.domain.models.challenge

data class Challenge(
    val id: Int,
    val challengeName: String,
    val pointsGained: Int,
    val goal: Int,
    val stepsTaken: Int,
    val timeTillEnd: String,
    val isShown: Boolean = false,
    val isStarted: Boolean = false
) {
    val progress: Int
        get() = pointsGained*100/goal
}