package com.puj.stepsfitnessgame.domain.usecases.challengelevel

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.challengelevel.ChallengeLevel
import com.puj.stepsfitnessgame.domain.repositories.ChallengeLevelRepository

class GetLevelListUseCase(
    private val repository: ChallengeLevelRepository
) {

    operator fun invoke(): LiveData<List<ChallengeLevel>> {
        return repository.getLevelList()
    }
}