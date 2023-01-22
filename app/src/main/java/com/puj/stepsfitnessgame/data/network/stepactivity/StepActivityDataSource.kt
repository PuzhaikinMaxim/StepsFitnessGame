package com.puj.stepsfitnessgame.data.network.stepactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.domain.models.statistics.TodayStatistics
import com.puj.stepsfitnessgame.domain.models.stepactivity.StepData
import java.time.LocalDateTime

class StepActivityDataSource {

    private val stepActivityDataProvider: StepActivityDataProvider by lazy {
        GoogleFitDataProvider()
    }

    fun getTodayStepCount(): Int {
        TODO()
    }

    suspend fun getStepCountInInterval(
        start: LocalDateTime,
        end: LocalDateTime,
    ): Int {
        return stepActivityDataProvider.getStepCountInInterval(start, end)
    }

    fun getTodayStepData(): LiveData<TodayStatistics> {
        val statisticsLd = MutableLiveData<TodayStatistics>()
        return statisticsLd
    }
}