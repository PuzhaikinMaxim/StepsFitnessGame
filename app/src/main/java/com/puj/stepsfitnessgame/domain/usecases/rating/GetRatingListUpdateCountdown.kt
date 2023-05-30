package com.puj.stepsfitnessgame.domain.usecases.rating

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.repositories.RatingRepository

class GetRatingListUpdateCountdown(private val ratingRepository: RatingRepository) {

    operator fun invoke(): LiveData<String> {
        return ratingRepository.getRatingListUpdateCountdown()
    }
}