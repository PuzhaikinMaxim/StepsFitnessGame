package com.puj.stepsfitnessgame.data.network.characteristics

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.characteristics.Characteristics

interface CharacteristicsRemoteDataSource {

    suspend fun getCharacteristics(): Response<Characteristics>

    suspend fun increaseEndurance()

    suspend fun increaseStrength()
}