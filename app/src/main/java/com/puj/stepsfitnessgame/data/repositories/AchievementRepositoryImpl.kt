package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.stepsfitnessgame.data.network.achievement.AchievementDto
import com.puj.stepsfitnessgame.data.network.achievement.AchievementMapper
import com.puj.stepsfitnessgame.data.network.achievement.AchievementRemoteDataSource
import com.puj.stepsfitnessgame.data.network.achievement.AchievementRemoteDataSourceImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.achievement.Achievement
import com.puj.stepsfitnessgame.domain.repositories.AchievementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AchievementRepositoryImpl(sharedPreferences: SharedPreferences): AchievementRepository {

    private val achievementList = MutableLiveData<List<AchievementDto>>()

    private val mapper = AchievementMapper()

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val achievementRemoteDataSource: AchievementRemoteDataSource = AchievementRemoteDataSourceImpl(token)

    override fun getAchievementList(): LiveData<List<Achievement>> {
        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch {
            val response = achievementRemoteDataSource.getAchievementList()
            if(response is Response.Success){
                achievementList.postValue(response.data)
            }
        }

        return Transformations.map(achievementList) {
            mapper.mapAchievementDtoListToAchievementList(it)
        }
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}