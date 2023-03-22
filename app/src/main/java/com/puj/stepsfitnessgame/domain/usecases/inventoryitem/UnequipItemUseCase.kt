package com.puj.stepsfitnessgame.domain.usecases.inventoryitem

import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.repositories.InventoryRepository

class UnequipItemUseCase(private val repository: InventoryRepository) {

    suspend operator fun invoke(slot: Int): Response<Unit> {
        return repository.unequipItem(slot)
    }
}