package com.puj.stepsfitnessgame.domain.models.item

data class InventoryItem(
    val inventoryId: Int,
    val itemId: Int,
    val itemName: String,
    val plusMinutes: Int,
    val timeMultiplier: Double,
    val pointsFixed: Int,
    val pointsMultiplier: Double,
    val rarity: Int = 1,
    var isEquipped: Boolean = false
)