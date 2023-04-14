package com.puj.stepsfitnessgame.domain.models.stepstatistics

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class StepData(
    val stepAmount: Int,
    val metersAmount: Int,
    val date: LocalDate,
    val averageStepAmount: Int,
    val inUiRepresentation: String = ""
) {

    val formattedDate: String
        get() = date.format(formatter)

    val kilometersPassed: String
        get() = (metersAmount.toDouble() / 1000).toString()

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