package com.puj.stepsfitnessgame.data.network.inventoryitem

import com.puj.stepsfitnessgame.domain.models.item.InventoryItem

class InventoryItemMapper {

    fun mapInventoryItemDtoListToInventoryItemList(
        list: List<InventoryItemDto>
    ): List<InventoryItem> {
        val inventoryItemList = ArrayList<InventoryItem>()
        for(item in list){
            inventoryItemList.add(mapInventoryItemDtoToInventoryItem(item))
        }
        return inventoryItemList
    }

    fun mapInventoryItemDtoListToEquippedItemArray(
        list: List<InventoryItemDto>
    ): Array<InventoryItem?> {
        val equippedItemsArray = Array<InventoryItem?>(2) { null }
        list.forEach {
            if(it.isEquipped && it.slot != null){
                equippedItemsArray.set(it.slot,mapInventoryItemDtoToInventoryItem(it))
            }
        }
        return equippedItemsArray
    }

    fun mapInventoryItemDtoToInventoryItem(
        itemDto: InventoryItemDto
    ): InventoryItem {
        return InventoryItem(
            itemDto.inventoryId,
            itemDto.itemId,
            itemDto.itemName,
            itemDto.plusTimeMinutes,
            itemDto.timeMultiplier,
            itemDto.pointsFixed,
            itemDto.pointsMultiplier,
            itemDto.rarityLevel,
            itemDto.isEquipped,
            itemDto.imageId
        )
    }
}