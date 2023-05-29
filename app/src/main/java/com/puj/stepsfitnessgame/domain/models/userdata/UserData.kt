package com.puj.stepsfitnessgame.domain.models.userdata

data class UserData(
    val username: String,
    val profileImageId: Int,
    val level: Int,
    val amountOfXp: Int,
    val amountToGain: Int
) {

    val progress: Int
        get() = amountOfXp * 100/amountToGain
}