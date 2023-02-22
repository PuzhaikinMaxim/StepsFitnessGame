package com.puj.stepsfitnessgame.data.network.dailytask

import com.puj.stepsfitnessgame.domain.models.dailytask.DailyTask

class FakeDailyTaskRemoteDataSource(
    token: String
): DailyTaskRemoteDataSource {

    private var dailyTaskProgress = 200

    private val dailyTasks = arrayListOf(
        DailyTask("Наберите 200 шагов за день", true),
        DailyTask("Наберите 500 шагов за день", false),
        DailyTask("Наберите 1000 шагов за день", false),
        DailyTask("Наберите 5000 шагов за день", false),
        DailyTask("Наберите 10000 шагов за день", false),
        DailyTask("Наберите 15000 шагов за день", false),
    )

    override suspend fun getDailyChallengeList(): List<DailyTask> {
        return dailyTasks
    }

    override suspend fun updateDailyChallengesProgress(stepCount: Int) {
        dailyTaskProgress += stepCount
        if(dailyTaskProgress >= 500) dailyTasks[1] = dailyTasks[1].copy(isCompleted = true)
        if(dailyTaskProgress >= 1000) dailyTasks[2] = dailyTasks[2].copy(isCompleted = true)
        if(dailyTaskProgress >= 5000) dailyTasks[3] = dailyTasks[3].copy(isCompleted = true)
        if(dailyTaskProgress >= 10000) dailyTasks[4] = dailyTasks[4].copy(isCompleted = true)
        if(dailyTaskProgress >= 15000) dailyTasks[4] = dailyTasks[4].copy(isCompleted = true)
    }
}