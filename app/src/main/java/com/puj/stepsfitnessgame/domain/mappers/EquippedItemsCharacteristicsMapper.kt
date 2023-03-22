package com.puj.stepsfitnessgame.domain.mappers

import com.puj.stepsfitnessgame.domain.models.characteristics.Characteristics
import com.puj.stepsfitnessgame.domain.models.item.EquippedItemsCharacteristics
import com.puj.stepsfitnessgame.domain.models.item.InventoryItem

class EquippedItemsCharacteristicsMapper {

    fun mapEquippedItemsArrayToEquippedItemsCharacteristics(
        array: Array<InventoryItem?>?
    ): EquippedItemsCharacteristics {
        if(array == null){
            return getEmptyCharacteristics()
        }
        var plusMinutes = 0
        var timeMultiplier = 0.0
        var pointsFixed = 0
        var pointsMultiplier = 0.0
        for(item in array){
            if(item == null) {
                continue
            }
            plusMinutes += item.plusMinutes
            timeMultiplier += item.timeMultiplier
            pointsFixed += item.pointsFixed
            pointsMultiplier += item.pointsMultiplier
        }
        return EquippedItemsCharacteristics(
            plusMinutes, timeMultiplier, pointsFixed, pointsMultiplier
        )
    }

    private fun getEmptyCharacteristics(): EquippedItemsCharacteristics {
        return EquippedItemsCharacteristics(
            0,0.0,0,0.0
        )
    }
}