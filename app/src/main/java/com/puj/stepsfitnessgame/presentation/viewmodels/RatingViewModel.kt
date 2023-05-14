package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.RatingRepositoryImpl
import com.puj.stepsfitnessgame.domain.repositories.RatingRepository
import com.puj.stepsfitnessgame.domain.usecases.rating.GetRatingListUseCase
import com.puj.stepsfitnessgame.domain.usecases.rating.SetDuelsAmountRatingUseCase
import com.puj.stepsfitnessgame.domain.usecases.rating.SetStepAmountRatingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RatingViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val repository: RatingRepository = RatingRepositoryImpl(sharedPreferences)

    private val setStepAmountRatingUseCase = SetStepAmountRatingUseCase(repository)

    private val setDuelsAmountRatingUseCase = SetDuelsAmountRatingUseCase(repository)

    private val getRatingListUseCase = GetRatingListUseCase(repository)

    val ratingList = getRatingListUseCase()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            setStepAmountRatingUseCase()
        }
    }

    fun setStepAmountRating() {
        viewModelScope.launch(Dispatchers.IO) {
            setStepAmountRatingUseCase()
        }
    }

    fun setDuelsAmountRating() {
        viewModelScope.launch(Dispatchers.IO) {
            setDuelsAmountRatingUseCase()
        }
    }
}