package com.puj.stepsfitnessgame.data.network.stepactivity

import com.puj.stepsfitnessgame.domain.models.stepactivity.StepData
import java.time.LocalDateTime

interface StepActivityDataProvider {

    suspend fun getTodayStepCount(): Int

    suspend fun getTodayMetersPassed(): Int

    suspend fun getStepCountInInterval(
        start: LocalDateTime,
        end: LocalDateTime
    ): Int

    suspend fun getTodayStepData(): StepData
}