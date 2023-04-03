package com.puj.stepsfitnessgame.domain.models.achievement

data class Achievement(
    val achievementId: Int,
    val achievementName: String,
    val achievementDescription: String,
    val achievementType: Int,
    val isCompleted: Boolean
) {

    companion object {
        const val achievementTypeStep = 1
        const val achievementTypeChallenge = 2
        const val achievementTypeDuel = 3
    }
}