package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.dailytask.DailyTask

interface DailyTasksRepository {

    fun getDailyChallengesList(): LiveData<List<DailyTask>>

}