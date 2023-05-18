package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.rating.RatingDataProviderImpl
import com.puj.stepsfitnessgame.data.network.rating.RatingMapper
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.rating.Rating
import com.puj.stepsfitnessgame.domain.repositories.RatingRepository

class RatingRepositoryImpl(private val sharedPreferences: SharedPreferences): RatingRepository {

    private val ratingMapper = RatingMapper()

    private val ratingList = MutableLiveData<List<Rating>>()

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val ratingDataProvider = RatingDataProviderImpl(token)

    override fun getRatingListUseCase(): LiveData<List<Rating>> {
        return ratingList
    }

    override suspend fun setStepAmountRating() {
        val response = ratingDataProvider.getStepAmountRating()
        if(response is Response.Success){
            ratingList.postValue(ratingMapper.mapRatingDtoListToRatingList(
                response.data,
                Rating.RatingType.TYPE_STEPS
            ))
        }
    }

    override suspend fun setDuelsAmountRating() {
        val response = ratingDataProvider.getDuelsAmountRating()
        if(response is Response.Success){
            ratingList.postValue(ratingMapper.mapRatingDtoListToRatingList(
                response.data,
                Rating.RatingType.TYPE_DUELS
            ))
        }
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}