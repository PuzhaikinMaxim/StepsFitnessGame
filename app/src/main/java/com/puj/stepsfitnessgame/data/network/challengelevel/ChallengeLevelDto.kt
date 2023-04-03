package com.puj.stepsfitnessgame.data.network.challengelevel

import com.google.gson.annotations.SerializedName

data class ChallengeLevelDto(
    val dungeonLevel: Int,
    val amountOfChallenges: Int,
    val amountOfCompletedChallenges: Int,
    @SerializedName("locked")
    val isLocked: Boolean,
    val minimalLevelRequirements: Int
) {
}