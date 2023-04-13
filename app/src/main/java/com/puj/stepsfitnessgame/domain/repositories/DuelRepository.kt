package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics

interface DuelRepository {

    fun startDuelSearch(): LiveData<Boolean>

    fun stopDuelSearch()

    fun getDuelField(): LiveData<DuelField>

    fun getDuelStatistics(): LiveData<DuelStatistics>
}