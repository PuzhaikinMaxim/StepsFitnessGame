package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.rating.Rating

interface RatingRepository {

    fun getRatingListUseCase(): LiveData<List<Rating>>

    suspend fun setStepAmountRating()

    suspend fun setDuelsAmountRating()
}