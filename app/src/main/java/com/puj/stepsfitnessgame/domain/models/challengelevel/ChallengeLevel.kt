package com.puj.stepsfitnessgame.domain.models.challengelevel

import java.lang.Integer.max

data class ChallengeLevel(
    val dungeonLevel: Int,
    val amountOfChallenges: Int,
    val amountOfCompletedChallenges: Int,
    val isLocked: Boolean,
    val minimalLevelRequirements: Int
) {

    val progress: Int
        get() = (amountOfCompletedChallenges * 100)/max(amountOfChallenges,1)
}