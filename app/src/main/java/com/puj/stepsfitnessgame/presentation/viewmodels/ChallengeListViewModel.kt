package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

class ChallengeListViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val _challengeList = MutableLiveData<List<Challenge>>()
    val challengeList: LiveData<List<Challenge>>
        get() = _challengeList

    init {
        val challengeList = arrayListOf<Challenge>(
            Challenge(
                "Испытание1",20,100,1000,"5ч 30 мин"
            ),
            Challenge(
                "Испытание2",50,100,1000,"5ч 30 мин"
            ),
            Challenge(
                "Испытание3",70,100,1000,"5ч 30 мин"
            ),
            Challenge(
                "Испытание4",90,100,1000,"5ч 30 мин"
            ),
        )
        _challengeList.value = challengeList
    }
}