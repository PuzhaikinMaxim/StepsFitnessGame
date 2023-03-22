package com.puj.stepsfitnessgame.domain.models.item

data class EquippedItemsCharacteristics(
    val plusMinutes: Int,
    val timeMultiplier: Double,
    val pointsFixed: Int,
    val pointsMultiplier: Double
) {
}