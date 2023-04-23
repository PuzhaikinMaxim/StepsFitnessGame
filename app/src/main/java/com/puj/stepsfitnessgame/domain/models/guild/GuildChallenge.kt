package com.puj.stepsfitnessgame.domain.models.guild

data class GuildChallenge(
    val challengeId: Long,
    val goal: Int,
    val hoursToFinish: Int
) {

    val hoursToFinishString: String
        get() = "${hoursToFinish}ч"
}