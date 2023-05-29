package com.puj.stepsfitnessgame.data.network.rating

data class RatingDto(
    val id: Int,
    val playerName: String,
    val playerLevel: Int,
    val profileImageId: Int,
    val place: Int?,
    val amountOfSteps: Int,
    val amountOfDuelsWon: Int
) {
}