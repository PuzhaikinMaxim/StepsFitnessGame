package com.puj.stepsfitnessgame.data.network.duel

import com.puj.stepsfitnessgame.data.network.item.ItemMapper
import com.puj.stepsfitnessgame.domain.models.duel.FinishedDuelReward

class FinishedDuelRewardMapper {

    private val itemMapper = ItemMapper()

    fun mapFinishedDuelRewardDtoToFinishedDuelReward(
        finishedDuelRewardDto: FinishedDuelRewardDto
    ): FinishedDuelReward {
        return FinishedDuelReward(
            finishedDuelRewardDto.xp,
            itemMapper.mapItemsListDtoToItemsList(finishedDuelRewardDto.reward)
        )
    }
}