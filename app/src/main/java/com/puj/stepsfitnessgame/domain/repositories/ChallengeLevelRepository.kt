package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.challengelevel.ChallengeLevel

interface ChallengeLevelRepository {

    fun getLevelList(): LiveData<List<ChallengeLevel>>

    fun selectLevel(level: Int)
}