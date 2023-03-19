package com.puj.stepsfitnessgame.data.network.dailychallenge

import com.google.gson.annotations.SerializedName

data class DailyChallengeDto(
    val amountOfSteps: Int,
    @SerializedName("completed")
    val isCompleted: Boolean
) {
}