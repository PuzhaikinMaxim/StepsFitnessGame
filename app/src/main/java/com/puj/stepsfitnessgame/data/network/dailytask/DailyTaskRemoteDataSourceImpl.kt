package com.puj.stepsfitnessgame.data.network.dailytask

import com.puj.stepsfitnessgame.domain.models.dailytask.DailyTask

class DailyTaskRemoteDataSourceImpl(
    token: String
): DailyTaskRemoteDataSource {

    override suspend fun getDailyChallengeList(): List<DailyTask> {
        TODO("Not yet implemented")
    }

    override suspend fun updateDailyChallengesProgress(stepCount: Int) {
        TODO("Not yet implemented")
    }
}