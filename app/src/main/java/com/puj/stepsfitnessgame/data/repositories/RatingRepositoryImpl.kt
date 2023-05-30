package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.rating.RatingDataSourceImpl
import com.puj.stepsfitnessgame.data.network.rating.RatingMapper
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.rating.Rating
import com.puj.stepsfitnessgame.domain.repositories.RatingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RatingRepositoryImpl(private val sharedPreferences: SharedPreferences): RatingRepository {

    private val ratingMapper = RatingMapper()

    private val ratingList = MutableLiveData<List<Rating>>()

    private val countdown = MutableLiveData<String>()

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val ratingDataSource = RatingDataSourceImpl(token)

    override fun getRatingListUseCase(): LiveData<List<Rating>> {
        return ratingList
    }

    override fun getRatingListUpdateCountdown(): LiveData<String> {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ratingDataSource.getRatingListUpdateCountdown()
            if(response is Response.Success){
                countdown.postValue(response.data)
            }
        }
        return countdown
    }

    override suspend fun setStepAmountRating() {
        val response = ratingDataSource.getStepAmountRating()
        if(response is Response.Success){
            ratingList.postValue(ratingMapper.mapRatingDtoListToRatingList(
                response.data,
                Rating.RatingType.TYPE_STEPS
            ))
        }
    }

    override suspend fun setDuelsAmountRating() {
        val response = ratingDataSource.getDuelsAmountRating()
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