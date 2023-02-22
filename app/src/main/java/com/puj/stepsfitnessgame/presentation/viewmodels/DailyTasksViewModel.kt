package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.puj.stepsfitnessgame.data.repositories.DailyTasksRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.dailytask.DailyTask
import com.puj.stepsfitnessgame.domain.repositories.DailyTasksRepository
import com.puj.stepsfitnessgame.domain.usecases.dailychallenge.GetDailyTasksUseCase

class DailyTasksViewModel(
    sharedPreferences: SharedPreferences
): ViewModel() {

    private val repository: DailyTasksRepository = DailyTasksRepositoryImpl(sharedPreferences)

    private val getDailyTasksUseCase = GetDailyTasksUseCase(repository)

    private val _dailyTasksList = getDailyTasksUseCase()
    val dailyTasksList: LiveData<List<DailyTask>>
        get() = _dailyTasksList

    val amountOfCompletedTasks: Int
        get() = _dailyTasksList.value?.count { it.isCompleted } ?: 0

    val amountOfTasks: Int
        get() = _dailyTasksList.value?.size ?: 0
}