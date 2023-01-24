package com.puj.stepsfitnessgame.domain.models.statistics

data class TodayStatistics(
    val stepsPassed: Int,
    val metersPassed: Int,
    val dailyGoal: Int
) {

    val percentOfGoal: Int
        get() = stepsPassed * 100 / dailyGoal

    val kilometersPassed: Float
        get() = (metersPassed.toFloat() / 1000)
}