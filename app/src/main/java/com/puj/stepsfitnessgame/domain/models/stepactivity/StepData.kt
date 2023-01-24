package com.puj.stepsfitnessgame.domain.models.stepactivity

import java.time.LocalDate

data class StepData(
    val stepAmount: Int,
    val metersAmount: Int,
    val date: LocalDate
)