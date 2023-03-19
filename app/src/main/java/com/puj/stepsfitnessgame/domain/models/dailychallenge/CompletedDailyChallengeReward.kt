package com.puj.stepsfitnessgame.domain.models.dailychallenge

import com.puj.stepsfitnessgame.domain.models.item.Item

class CompletedDailyChallengeReward(
    val xp: Int,
    val reward: List<Item>
) {
}