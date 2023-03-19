package com.puj.stepsfitnessgame.domain.models.challenge

import com.puj.stepsfitnessgame.domain.models.item.Item

data class CompletedChallengeReward(
    val amountOfXp: Int,
    val itemsList: List<Item>
) {
}