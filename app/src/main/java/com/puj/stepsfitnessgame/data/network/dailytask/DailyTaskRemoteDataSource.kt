package com.puj.stepsfitnessgame.data.network.dailytask

import com.puj.stepsfitnessgame.domain.models.dailytask.DailyTask

interface DailyTaskRemoteDataSource {

    suspend fun getDailyChallengeList(): List<DailyTask>

    suspend fun updateDailyChallengesProgress(stepCount: Int)
}