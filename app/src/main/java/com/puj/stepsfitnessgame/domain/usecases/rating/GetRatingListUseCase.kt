package com.puj.stepsfitnessgame.domain.usecases.rating

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.rating.Rating
import com.puj.stepsfitnessgame.domain.repositories.RatingRepository

class GetRatingListUseCase(private val repository: RatingRepository) {

    operator fun invoke(): LiveData<List<Rating>> {
        return repository.getRatingListUseCase()
    }
}