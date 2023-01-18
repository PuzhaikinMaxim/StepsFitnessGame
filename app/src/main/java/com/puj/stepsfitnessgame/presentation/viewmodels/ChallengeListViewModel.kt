package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

class ChallengeListViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val _challengeList = MutableLiveData<MutableList<Challenge>>()
    val challengeList: LiveData<out List<Challenge>>
        get() = _challengeList

    init {
        val challengeList = arrayListOf(
            Challenge(
                0,"Испытание1",20,100,1000,"5ч 30 мин"
            ),
            Challenge(
                1,"Испытание2",50,100,1000,"5ч 30 мин"
            ),
            Challenge(
                2,"Испытание3",70,100,1000,"5ч 30 мин"
            ),
            Challenge(
                3,"Испытание4",90,100,1000,"5ч 30 мин"
            ),
        )
        _challengeList.value = challengeList
    }

    fun changeChallengeDetailsVisibility(challenge: Challenge) {
        val newList = _challengeList.value?.toMutableList()
        newList?.set(challenge.id, challenge.copy(isShown = !challenge.isShown))
        _challengeList.value = newList
    }

    fun startChallenge(challenge: Challenge) {
        val newList = _challengeList.value?.toMutableList()
        newList?.set(challenge.id, challenge.copy(isStarted = true))
        _challengeList.value = newList
    }
}