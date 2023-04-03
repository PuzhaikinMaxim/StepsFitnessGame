package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.stepsfitnessgame.data.network.challengelevel.ChallengeLevelDto
import com.puj.stepsfitnessgame.data.network.challengelevel.ChallengeLevelMapper
import com.puj.stepsfitnessgame.data.network.challengelevel.ChallengeLevelRemoteDataSourceImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.challengelevel.ChallengeLevel
import com.puj.stepsfitnessgame.domain.repositories.ChallengeLevelRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeLevelRepositoryImpl(
    private val sharedPreferences: SharedPreferences
): ChallengeLevelRepository {

    private val levelList = MutableLiveData<List<ChallengeLevelDto>>()

    private val mapper = ChallengeLevelMapper()

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val levelDataSource = ChallengeLevelRemoteDataSourceImpl(token)

    override fun getLevelList(): LiveData<List<ChallengeLevel>> {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val response = levelDataSource.getLevelList()
            if(response is Response.Success){
                levelList.postValue(response.data)
            }
        }
        return Transformations.map(levelList) {
            mapper.mapChallengeLevelDtoListToChallengeLevelList(it)
        }
    }

    override fun selectLevel(level: Int) {
        sharedPreferences.edit().putInt(LEVEL_KEY, level).apply()
    }

    companion object {
        private const val LEVEL_KEY = "selectedLevel"
        private const val LEVEL_DEFAULT = 1
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}