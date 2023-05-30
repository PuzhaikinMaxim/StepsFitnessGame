package com.puj.stepsfitnessgame.data.network.rating

import com.puj.stepsfitnessgame.domain.models.Response
import retrofit2.http.Header

interface RatingDataSource {

    suspend fun getStepAmountRating(): Response<List<RatingDto>>

    suspend fun getDuelsAmountRating(): Response<List<RatingDto>>

    suspend fun getRatingListUpdateCountdown(): Response<String>
}