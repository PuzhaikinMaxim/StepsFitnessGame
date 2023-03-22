package com.puj.stepsfitnessgame.domain.usecases.inventoryitem

import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.domain.models.item.InventoryItem
import com.puj.stepsfitnessgame.domain.repositories.InventoryRepository

class GetEquippedItemArrayUseCase(private val repository: InventoryRepository) {

    operator fun invoke(): MutableLiveData<Array<InventoryItem?>> {
        return repository.getEquippedItems()
    }
}