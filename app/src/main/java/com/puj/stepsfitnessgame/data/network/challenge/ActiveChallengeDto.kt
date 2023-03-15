package com.puj.stepsfitnessgame.data.network.challenge

data class ActiveChallengeDto(
    val challengeId: Int,
    val challengeName: String,
    val amountOfPoints: Int,
    val challengeEndDateTime: String,
    val progress: Int,
    val amountOfSteps: Int,
    val challengeLevel: Int,
    val isCompleted: Boolean,
    val isFailed: Boolean
) {

}