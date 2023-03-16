package com.puj.stepsfitnessgame.data.network.item

import com.google.gson.annotations.SerializedName

data class ItemDto(
    val itemId: Int,
    val itemName: String,
    @SerializedName("plusTimeMinutes")
    val plusMinutes: Int,
    val timeMultiplier: Double,
    val pointsFixed: Int,
    val pointsMultiplier: Double
) {

}