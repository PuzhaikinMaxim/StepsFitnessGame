package com.puj.stepsfitnessgame.data.network.achievement

import com.google.gson.annotations.SerializedName

data class AchievementDto(
    val achievementId: Int,
    val achievementName: String,
    val achievementDescription: String,
    val achievementType: Int,
    @SerializedName("completed")
    val isCompleted: Boolean
) {
}