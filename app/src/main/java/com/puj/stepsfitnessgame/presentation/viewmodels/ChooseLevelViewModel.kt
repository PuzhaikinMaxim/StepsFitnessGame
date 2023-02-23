package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel

class ChooseLevelViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    val levelList = MutableLiveData<List<DungeonLevel>>()

    init {
        levelList.value = listOf(
            DungeonLevel(1,30,10,false,1),
            DungeonLevel(2,30,0,true,5),
            DungeonLevel(3,30,0,true,9),
            DungeonLevel(4,30,0,true,14),
            DungeonLevel(5,30,0,true,19),
        )
    }
}