package com.puj.stepsfitnessgame.domain.models.item

data class Item(
    val itemId: Int,
    val itemName: String,
    val plusMinutes: Int,
    val timeMultiplier: Double,
    val pointsFixed: Int,
    val pointsMultiplier: Double
) {
}