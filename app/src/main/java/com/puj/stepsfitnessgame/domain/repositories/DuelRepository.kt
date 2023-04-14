package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.domain.models.duel.DuelStatistics

interface DuelRepository {

    fun startDuelSearch()

    fun stopDuelSearch()

    fun getDuelField(): LiveData<DuelField>

    fun getIsOpponentFound(): LiveData<Boolean>

    fun getDuelStatistics(): LiveData<DuelStatistics>
}