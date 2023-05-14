package com.puj.stepsfitnessgame.domain.usecases.rating

import com.puj.stepsfitnessgame.domain.repositories.RatingRepository

class SetDuelsAmountRatingUseCase(private val ratingRepository: RatingRepository) {

    suspend operator fun invoke() {
        ratingRepository.setDuelsAmountRating()
    }
}