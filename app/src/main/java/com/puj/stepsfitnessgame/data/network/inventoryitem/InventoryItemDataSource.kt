package com.puj.stepsfitnessgame.data.network.inventoryitem

import com.puj.stepsfitnessgame.domain.models.Response

interface InventoryItemDataSource {

    suspend fun equipItem(inventoryId: Int, slot: Int): Response<Unit>

    suspend fun unequipItem(slot: Int): Response<Unit>

    suspend fun getInventoryItemList(): Response<List<InventoryItemDto>>
}