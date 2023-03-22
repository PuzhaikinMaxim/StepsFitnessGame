package com.puj.stepsfitnessgame.domain.usecases.inventoryitem

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.repositories.InventoryRepository

class EquipItemUseCase(private val repository: InventoryRepository) {

    suspend operator fun invoke(inventoryId: Int, slot: Int): Response<Unit> {
        return repository.equipItem(inventoryId, slot)
    }
}