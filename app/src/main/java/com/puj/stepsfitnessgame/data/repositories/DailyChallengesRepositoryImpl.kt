package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.stepsfitnessgame.data.network.dailychallenge.CompletedDailyChallengeRewardDataMapper
import com.puj.stepsfitnessgame.data.network.dailychallenge.DailyChallengeDto
import com.puj.stepsfitnessgame.data.network.dailychallenge.DailyChallengeMapper
import com.puj.stepsfitnessgame.data.network.dailychallenge.DailyChallengeRemoteDataSourceImpl
import com.puj.stepsfitnessgame.domain.models.dailychallenge.CompletedDailyChallengeReward
import com.puj.stepsfitnessgame.domain.models.dailychallenge.DailyChallenge
import com.puj.stepsfitnessgame.domain.repositories.DailyChallengesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyChallengesRepositoryImpl(
    sharedPreferences: SharedPreferences
): DailyChallengesRepository {

    private val token: String = sharedPreferences.getString(TOKEN_KEY, DEFAULT) ?: DEFAULT

    private val dailyChallengeRemoteDataSource = DailyChallengeRemoteDataSourceImpl(token)

    private val dailyChallenges = MutableLiveData<List<DailyChallengeDto>>()

    private val completedDailyChallengeRewardDataMapper = CompletedDailyChallengeRewardDataMapper()

    private val dailyChallengeMapper = DailyChallengeMapper()

    override fun getDailyChallengesList(): LiveData<List<DailyChallenge>> {
        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch {
            val dailyChallengeList = dailyChallengeRemoteDataSource.getDailyChallengeList()
            dailyChallenges.postValue(dailyChallengeList)
        }

        return Transformations.map(dailyChallenges) {
            dailyChallengeMapper.mapDailyChallengeDtoListToDailyChallengeList(it)
        }
    }

    override suspend fun claimDailyChallengesReward(): CompletedDailyChallengeReward? {
        val reward = dailyChallengeRemoteDataSource.claimDailyChallengesReward()
        return completedDailyChallengeRewardDataMapper.mapCompletedDailyChallengeDataDtoToCompletedDailyChallengeData(reward)
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val DEFAULT = "default"
    }

}