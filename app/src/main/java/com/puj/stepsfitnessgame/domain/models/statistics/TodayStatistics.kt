package com.puj.stepsfitnessgame.domain.models.statistics

data class TodayStatistics(
    val stepAmount: Int,
    val metersAmount: Int,
    val goal: Int
) {

    val percentOfGoal: Int
        get() = stepAmount * 100 / goal

    val kilometersPassed: String
        get() = (metersAmount.toDouble() / 1000).toString()
}