package com.puj.stepsfitnessgame.domain.usecases.inventoryitem

import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.domain.models.item.InventoryItem
import com.puj.stepsfitnessgame.domain.repositories.InventoryRepository

class GetInventoryItemListUseCase(private val repository: InventoryRepository) {

    operator fun invoke(): MutableLiveData<List<InventoryItem>> {
        return repository.getInventoryItems()
    }
}