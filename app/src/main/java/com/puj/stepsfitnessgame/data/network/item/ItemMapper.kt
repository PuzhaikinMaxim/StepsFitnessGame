package com.puj.stepsfitnessgame.data.network.item

import com.puj.stepsfitnessgame.domain.models.item.Item

class ItemMapper {

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
            itemDto.pointsMultiplier,
            itemDto.rarityLevel,
            itemDto.imageId
        )
    }
}