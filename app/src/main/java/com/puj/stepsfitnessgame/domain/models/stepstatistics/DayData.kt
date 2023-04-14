package com.puj.stepsfitnessgame.domain.models.stepstatistics

import java.time.LocalDate

data class DayData(
    val stepAmount: Int,
    val goal: Int,
    val date: LocalDate,
    val dayOfWeek: String
) {

    val percentOfGoal: Int
        get() = stepAmount * 100 / goal

    companion object {
        private val daysOfWeekList = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")

        fun getWeekRepresentation(dayOfWeek: Int): String {
            return daysOfWeekList[dayOfWeek - 1]
        }
    }
}