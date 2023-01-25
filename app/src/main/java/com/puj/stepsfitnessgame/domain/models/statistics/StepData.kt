package com.puj.stepsfitnessgame.domain.models.statistics

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class StepData(
    val stepAmount: Int,
    val metersAmount: Int,
    val date: LocalDate,
    val monthRepresentation: String = ""
) {

    val formattedDate: String
        get() = date.format(formatter)

    companion object {

        private val formatter = DateTimeFormatter.ofPattern("MM.dd")

        private val monthsList = listOf(
            "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"
        )

        fun getMonthRepresentation(month: Int): String {
            return monthsList[month - 1]
        }
    }
}