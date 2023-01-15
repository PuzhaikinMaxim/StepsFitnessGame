package com.puj.stepsfitnessgame.domain.models.statistics

data class TodayStatistics(
    val stepsPassed: Int,
    val metersPassed: Int,
    val dailyGoal: Int
) {

    val percentOfGoal: Int
        get() = metersPassed * 100 / dailyGoal
}