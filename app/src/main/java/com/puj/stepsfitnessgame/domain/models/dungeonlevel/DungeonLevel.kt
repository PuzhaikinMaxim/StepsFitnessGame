package com.puj.stepsfitnessgame.domain.models.dungeonlevel

data class DungeonLevel(
    val dungeonLevel: Int,
    val amountOfChallenges: Int,
    val amountOfCompletedChallenges: Int,
    val isLocked: Boolean,
    val minimalLevelRequirements: Int
) {
}