package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.data.network.dailychallenge.CompletedDailyChallengeRewardDto
import com.puj.stepsfitnessgame.domain.models.dailychallenge.CompletedDailyChallengeReward
import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge

interface DailyChallengesRepository {

    fun getDailyChallengesList(): LiveData<List<DailyChallenge>>

    suspend fun claimDailyChallengesReward(): CompletedDailyChallengeReward?
}