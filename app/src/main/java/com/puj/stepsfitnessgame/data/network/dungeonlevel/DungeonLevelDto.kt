package com.puj.stepsfitnessgame.data.network.dungeonlevel

import com.google.gson.annotations.SerializedName

data class DungeonLevelDto(
    val dungeonLevel: Int,
    val amountOfChallenges: Int,
    val amountOfCompletedChallenges: Int,
    @SerializedName("locked")
    val isLocked: Boolean,
    val minimalLevelRequirements: Int
) {
}