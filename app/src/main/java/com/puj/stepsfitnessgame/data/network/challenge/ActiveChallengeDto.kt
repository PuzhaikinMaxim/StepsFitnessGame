package com.puj.stepsfitnessgame.data.network.challenge

import com.google.gson.annotations.SerializedName

data class ActiveChallengeDto(
    val challengeId: Int,
    val challengeName: String,
    val amountOfPoints: Int,
    val challengeEndDateTime: String,
    val progress: Int,
    val amountOfSteps: Int,
    val challengeLevel: Int,
    @SerializedName("completed")
    val isCompleted: Boolean,
    @SerializedName("failed")
    val isFailed: Boolean
) {

}