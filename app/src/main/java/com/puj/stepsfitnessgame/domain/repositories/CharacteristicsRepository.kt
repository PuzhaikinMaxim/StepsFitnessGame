package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.characteristics.Characteristics

interface CharacteristicsRepository {

    fun getCharacteristics(): LiveData<Characteristics>

    suspend fun increaseEndurance()

    suspend fun increaseStrength()
}