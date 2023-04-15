package com.puj.stepsfitnessgame.data.network.duel

import com.puj.stepsfitnessgame.data.network.item.ItemDto

data class FinishedDuelRewardDto(
    val xp: Int,
    val reward: List<ItemDto>
) {

}
