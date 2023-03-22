package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.item.InventoryItem

interface InventoryRepository {

    suspend fun equipItem(inventoryId: Int, slot: Int): Response<Unit>

    suspend fun unequipItem(slot: Int): Response<Unit>

    fun getInventoryItems(): MutableLiveData<List<InventoryItem>>

    fun getEquippedItems(): MutableLiveData<Array<InventoryItem?>>
}