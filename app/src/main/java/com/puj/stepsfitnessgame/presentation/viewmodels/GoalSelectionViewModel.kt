package com.puj.stepsfitnessgame.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.StepStatisticsRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.stepstatistics.Goal
import com.puj.stepsfitnessgame.domain.usecases.stepstatistics.goal.GetGoalsListUseCase
import com.puj.stepsfitnessgame.domain.usecases.stepstatistics.goal.SetGoalUseCase
import com.puj.stepsfitnessgame.domain.usecases.stepstatistics.goal.SetGoalsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoalSelectionViewModel: ViewModel() {

    private val repository = StepStatisticsRepositoryImpl()
    private val getGoalsListUseCase = GetGoalsListUseCase(repository)
    private val setGoalsListUseCase = SetGoalsListUseCase(repository)
    private val setGoalUseCase = SetGoalUseCase(repository)

    private val _goalList = getGoalsListUseCase()
    val goalList: LiveData<List<Goal>>
        get() = _goalList

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    init {
        viewModelScope.launch(Dispatchers.Default) {
            setGoalsListUseCase()
        }
    }

    fun setGoal(goal: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            setGoalUseCase(goal)
            _shouldCloseScreen.postValue(Unit)
        }
    }
}