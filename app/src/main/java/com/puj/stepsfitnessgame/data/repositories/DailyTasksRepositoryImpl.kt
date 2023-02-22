package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.dailytask.FakeDailyTaskRemoteDataSource
import com.puj.stepsfitnessgame.domain.models.dailytask.DailyTask
import com.puj.stepsfitnessgame.domain.repositories.DailyTasksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyTasksRepositoryImpl(
    sharedPreferences: SharedPreferences
): DailyTasksRepository {

    private val token: String = sharedPreferences.getString(TOKEN_KEY, DEFAULT) ?: DEFAULT

    private val dailyTaskRemoteDataSource = FakeDailyTaskRemoteDataSource(token)

    private val dailyTasks = MutableLiveData<List<DailyTask>>()

    override fun getDailyChallengesList(): LiveData<List<DailyTask>> {
        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch {
            val dailyTaskList = dailyTaskRemoteDataSource.getDailyChallengeList()
            dailyTasks.postValue(dailyTaskList)
        }

        return dailyTasks
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val DEFAULT = "default"
    }

}