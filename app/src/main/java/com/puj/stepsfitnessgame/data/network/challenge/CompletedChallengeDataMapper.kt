package com.puj.stepsfitnessgame.data.network.challenge

import com.puj.stepsfitnessgame.data.network.item.ItemDto
import com.puj.stepsfitnessgame.domain.models.challenge.CompletedChallengeData
import com.puj.stepsfitnessgame.domain.models.item.Item

class CompletedChallengeDataMapper {

    fun mapCompletedChallengeDataDtoToCompletedChallengeData(
        completedChallengeDataDto: CompletedChallengeDataDto
    ): CompletedChallengeData {
        return CompletedChallengeData(
            completedChallengeDataDto.amountOfXp,
            mapItemsListDtoToItemsList(completedChallengeDataDto.items)
        )
    }

    fun mapItemsListDtoToItemsList(list: List<ItemDto>): List<Item> {
        val itemList = ArrayList<Item>()
        for(itemDto in list){
            itemList.add(mapItemDtoToItem(itemDto))
        }

        return itemList
    }

    fun mapItemDtoToItem(itemDto: ItemDto): Item {
        return Item(
            itemDto.itemId,
            itemDto.itemName,
            itemDto.plusMinutes,
            itemDto.timeMultiplier,
            itemDto.pointsFixed,
            itemDto.pointsMultiplier
        )
    }
}