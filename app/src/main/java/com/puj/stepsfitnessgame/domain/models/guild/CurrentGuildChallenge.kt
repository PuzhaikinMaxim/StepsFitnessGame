package com.puj.stepsfitnessgame.domain.models.guild

data class CurrentGuildChallenge(
    val pointsGained: Int,
    val goal: Int,
    val timeTillEnd: String
) {
    val progress: Int
        get() = pointsGained*100/goal
}