package com.puj.stepsfitnessgame.data.network.rating

import com.puj.stepsfitnessgame.domain.models.Response

interface RatingDataSource {

    suspend fun getStepAmountRating(): Response<List<RatingDto>>

    suspend fun getDuelsAmountRating(): Response<List<RatingDto>>
}