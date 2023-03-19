package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.data.network.item.ItemMapper
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeReward

class CompletedChallengeDataMapper {

    private val itemMapper = ItemMapper()

    fun mapCompletedChallengeDataDtoToCompletedChallengeData(
        completedChallengeRewardDto: CompletedChallengeRewardDto
    ): CompletedChallengeReward {
        return CompletedChallengeReward(
            completedChallengeRewardDto.amountOfXp,
            itemMapper.mapItemsListDtoToItemsList(completedChallengeRewardDto.items)
        )
    }
}