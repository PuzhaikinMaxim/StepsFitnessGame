package com.puj.stepsfitnessgame.domain.models.statistics

data class TodayStatistics(
    val stepAmount: Int,
    val metersAmount: Int,
    val goal: Int
) {

    val percentOfGoal: Int
        get() = stepAmount * 100 / goal

    val kilometersPassed: Float
        get() = (metersAmount.toFloat() / 1000)
}