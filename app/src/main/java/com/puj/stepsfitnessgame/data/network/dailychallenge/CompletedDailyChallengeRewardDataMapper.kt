package com.puj.stepsfitnessgame.data.network.dailychallenge

import com.puj.stepsfitnessgame.data.network.item.ItemMapper
import com.puj.stepsfitnessgame.domain.models.dailychallenge.CompletedDailyChallengeReward

class CompletedDailyChallengeRewardDataMapper {

    private val itemMapper = ItemMapper()

    fun mapCompletedDailyChallengeDataDtoToCompletedDailyChallengeData(
        completedDailyChallengeRewardDto: CompletedDailyChallengeRewardDto?
    ): CompletedDailyChallengeReward? {
        if(completedDailyChallengeRewardDto == null){
            return null
        }

        with(completedDailyChallengeRewardDto){
            if(reward.isEmpty() && xp == 0){
                return null
            }
        }

        return CompletedDailyChallengeReward(
            completedDailyChallengeRewardDto.xp,
            itemMapper.mapItemsListDtoToItemsList(completedDailyChallengeRewardDto.reward)
        )
    }
}