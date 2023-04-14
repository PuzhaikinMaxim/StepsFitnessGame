package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics

interface PlayerStatisticsRepository {

    fun getDuelStatistics(): LiveData<DuelStatistics>
}