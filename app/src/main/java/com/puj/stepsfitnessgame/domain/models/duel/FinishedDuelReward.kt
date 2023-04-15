package com.puj.stepsfitnessgame.domain.models.duel

import com.puj.stepsfitnessgame.domain.models.item.Item

data class FinishedDuelReward(
    val xp: Int,
    val reward: List<Item>
) {
}