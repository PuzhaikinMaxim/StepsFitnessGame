package com.puj.stepsfitnessgame.data.network.dailychallenge

import com.puj.stepsfitnessgame.data.network.item.ItemDto

data class CompletedDailyChallengeRewardDto(
    val xp: Int,
    val reward: List<ItemDto>
) {
}