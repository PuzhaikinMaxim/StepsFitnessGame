package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.data.network.item.ItemDto

data class CompletedChallengeDataDto(
    val amountOfXp: Int,
    val items: List<ItemDto>
) {
}