package com.puj.stepsfitnessgame.data.network.stepactivity

import com.puj.stepsfitnessgame.domain.models.stepactivity.StepData
import java.time.LocalDate
import java.time.LocalDateTime

interface StepActivityDataProvider {

    fun getTodayStepCount(): Int

    fun getStepCountInInterval(
        start: LocalDateTime,
        end: LocalDateTime,
        onDataGotListener: (Int) -> (Unit)
    )

    fun getTodayStepData(): StepData
}