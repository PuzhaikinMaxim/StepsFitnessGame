package com.puj.stepsfitnessgame.data.stepactivity

import com.puj.stepsfitnessgame.domain.models.statistics.StepData
import java.time.LocalDateTime

interface StepActivityDataProvider {

    suspend fun getTodayStepCount(): Int

    suspend fun getTodayMetersPassed(): Int

    suspend fun getStepCountInInterval(
        start: LocalDateTime,
        end: LocalDateTime
    ): Int

    suspend fun getLastMonthsStatistics(
        amount: Int
    ): List<StepData>

    suspend fun getLastWeeksStatistics(
        amount: Int
    ): List<StepData>

    suspend fun getLastDaysStatistics(
        amount: Int
    ): List<StepData>
}