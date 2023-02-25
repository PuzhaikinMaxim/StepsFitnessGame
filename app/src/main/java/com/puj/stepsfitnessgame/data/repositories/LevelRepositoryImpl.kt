package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel
import com.puj.stepsfitnessgame.domain.repositories.LevelRepository

class LevelRepositoryImpl(
    private val sharedPreferences: SharedPreferences
): LevelRepository {

    val levelList = MutableLiveData<List<DungeonLevel>>()

    override fun getLevelList(): LiveData<List<DungeonLevel>> {
        levelList.value = listOf(
            DungeonLevel(1,30,10,false,1),
            DungeonLevel(2,30,0,true,5),
            DungeonLevel(3,30,0,true,9),
            DungeonLevel(4,30,0,true,14),
            DungeonLevel(5,30,0,true,19),
        )
        return levelList
    }

    override fun selectLevel(level: Int) {
        sharedPreferences.edit().putInt(LEVEL_KEY, level).apply()
    }

    companion object {
        private const val LEVEL_KEY = "selectedLevel"
        private const val DEFAULT = 1
    }
}