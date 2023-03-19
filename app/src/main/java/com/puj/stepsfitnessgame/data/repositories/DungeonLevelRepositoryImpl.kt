package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.stepsfitnessgame.data.network.dungeonlevel.DungeonLevelDto
import com.puj.stepsfitnessgame.data.network.dungeonlevel.DungeonLevelMapper
import com.puj.stepsfitnessgame.data.network.dungeonlevel.LevelRemoteDataSourceImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel
import com.puj.stepsfitnessgame.domain.repositories.DungeonLevelRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DungeonLevelRepositoryImpl(
    private val sharedPreferences: SharedPreferences
): DungeonLevelRepository {

    private val levelList = MutableLiveData<List<DungeonLevelDto>>()

    private val mapper = DungeonLevelMapper()

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val levelDataSource = LevelRemoteDataSourceImpl(token)

    override fun getLevelList(): LiveData<List<DungeonLevel>> {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val response = levelDataSource.getLevelList()
            if(response is Response.Success){
                levelList.postValue(response.data)
            }
        }
        return Transformations.map(levelList) {
            mapper.mapDungeonLevelDtoListToDungeonLevelList(it)
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