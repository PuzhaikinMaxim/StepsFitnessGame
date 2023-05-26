package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.stepsfitnessgame.data.network.inventoryitem.InventoryItemDataSourceImpl
import com.puj.stepsfitnessgame.data.network.inventoryitem.InventoryItemDto
import com.puj.stepsfitnessgame.data.network.inventoryitem.InventoryItemMapper
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.item.InventoryItem
import com.puj.stepsfitnessgame.domain.repositories.InventoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InventoryRepositoryImpl(private val sharedPreferences: SharedPreferences): InventoryRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val inventoryItemDataSource = InventoryItemDataSourceImpl(token)

    private val inventoryItemsList = MutableLiveData<List<InventoryItemDto>>()

    private val equippedItems = MutableLiveData<Array<InventoryItemDto>>()

    private val inventoryItemMapper = InventoryItemMapper()

    override suspend fun equipItem(inventoryId: Int, slot: Int): Response<Unit> {
        return inventoryItemDataSource.equipItem(inventoryId, slot)
    }

    override suspend fun unequipItem(slot: Int): Response<Unit> {
        return inventoryItemDataSource.unequipItem(slot)
    }

    override fun getInventoryItems(): MutableLiveData<List<InventoryItem>> {
        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch {
            val response = inventoryItemDataSource.getInventoryItemList()
            if(response is Response.Success){
                inventoryItemsList.postValue(response.data)
            }
        }

        return Transformations.map(inventoryItemsList){
            inventoryItemMapper.mapInventoryItemDtoListToInventoryItemList(it)
        } as MutableLiveData<List<InventoryItem>>
    }

    override fun getEquippedItems(): MutableLiveData<Array<InventoryItem?>> {
        return Transformations.map(inventoryItemsList){
            inventoryItemMapper.mapInventoryItemDtoListToEquippedItemArray(it)
        } as MutableLiveData<Array<InventoryItem?>>
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}