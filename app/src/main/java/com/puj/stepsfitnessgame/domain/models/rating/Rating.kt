package com.puj.stepsfitnessgame.domain.models.rating

data class Rating(
    val id: Int,
    val playerName: String,
    val playerLevel: Int,
    val place: Int?,
    val amountOfSteps: Int,
    val amountOfDuelsWon: Int,
    val ratingType: RatingType
) {

    enum class RatingType {
        TYPE_STEPS,
        TYPE_DUELS
    }
}