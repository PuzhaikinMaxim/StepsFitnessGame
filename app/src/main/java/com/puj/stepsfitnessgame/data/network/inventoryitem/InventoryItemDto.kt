package com.puj.stepsfitnessgame.data.network.inventoryitem

import com.google.gson.annotations.SerializedName

data class InventoryItemDto(
    val inventoryId: Int,
    val itemId: Int,
    val itemName: String,
    val plusTimeMinutes: Int,
    val timeMultiplier: Double,
    val pointsFixed: Int,
    val pointsMultiplier: Double,
    val rarityLevel: Int,
    @SerializedName("equipped")
    val isEquipped: Boolean,
    val slot: Int?
) {
}