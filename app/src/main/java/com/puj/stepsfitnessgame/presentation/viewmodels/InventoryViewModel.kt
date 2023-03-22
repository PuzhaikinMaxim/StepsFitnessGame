package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.puj.stepsfitnessgame.data.repositories.InventoryRepositoryImpl
import com.puj.stepsfitnessgame.domain.mappers.EquippedItemsCharacteristicsMapper
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.item.InventoryItem
import com.puj.stepsfitnessgame.domain.models.item.EquippedItemsCharacteristics
import com.puj.stepsfitnessgame.domain.repositories.InventoryRepository
import com.puj.stepsfitnessgame.domain.usecases.inventoryitem.EquipItemUseCase
import com.puj.stepsfitnessgame.domain.usecases.inventoryitem.GetEquippedItemArrayUseCase
import com.puj.stepsfitnessgame.domain.usecases.inventoryitem.GetInventoryItemListUseCase
import com.puj.stepsfitnessgame.domain.usecases.inventoryitem.UnequipItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InventoryViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val repository = InventoryRepositoryImpl(sharedPreferences)

    private val equipItemUseCase = EquipItemUseCase(repository)

    private val unequipItemUseCase = UnequipItemUseCase(repository)

    private val getInventoryItemListUseCase = GetInventoryItemListUseCase(repository)

    private val getEquippedItemArrayUseCase = GetEquippedItemArrayUseCase(repository)

    private val _equippedItems = getEquippedItemArrayUseCase()
    val equippedItems: LiveData<Array<InventoryItem?>>
        get() = _equippedItems

    private val _items = getInventoryItemListUseCase()
    val items: LiveData<List<InventoryItem>>
        get() = _items

    private val _selectedItem = MutableLiveData<InventoryItem?>()
    val selectedItem: LiveData<InventoryItem?>
        get() = _selectedItem

    private val equippedItemsCharacteristicsMapper = EquippedItemsCharacteristicsMapper()

    val equippedItemsCharacteristics: LiveData<EquippedItemsCharacteristics>
        get() {
            return Transformations.map(_equippedItems){
                equippedItemsCharacteristicsMapper
                    .mapEquippedItemsArrayToEquippedItemsCharacteristics(it)
            }
        }

    fun equipItem(slot: Int){
        viewModelScope.launch(Dispatchers.Default) {
            val response = _selectedItem.value?.let { equipItemUseCase(it.inventoryId, slot + 1) }
            if(response is Response.Success){
                val oldItem =_equippedItems.value?.get(slot)
                val newList = _items.value?.toMutableList()
                if(oldItem != null) {
                    val oldItemCopy = newList!![oldItem.inventoryId]
                    oldItemCopy.isEquipped = false
                    //oldItem.isEquipped = false
                }
                _equippedItems.value?.set(slot, selectedItem.value)
                val newItemCopy = newList!![_selectedItem.value!!.inventoryId]
                newItemCopy.isEquipped = true
                //_selectedItem.value?.isEquipped = true
                updateInventoryItemsLiveData(newList)
                _selectedItem.postValue(null)
            }
        }
    }

    fun unequipItem(slot: Int) {
        val item = _equippedItems.value?.get(slot)
        if (item != null) {
            viewModelScope.launch(Dispatchers.Default) {
                val response = unequipItemUseCase(slot + 1)
                if (response is Response.Success) {
                    val newList = _items.value?.toMutableList()
                    val newItem = newList!![item.inventoryId]
                    newItem.isEquipped = false

                    _equippedItems.value?.set(slot, null)
                    updateInventoryItemsLiveData(newList)
                    _selectedItem.postValue(null)
                }
                else{
                    println("Response failure")
                }
            }
        }
    }

    fun selectItem(inventoryId: Int) {
        _selectedItem.value = _items.value?.find { it -> it.inventoryId == inventoryId }
    }

    fun unselectItem() {
        _selectedItem.value = null
    }

    fun getEquipmentSlot(inventoryId: Int): Int? {
        val equippedItems = _equippedItems.value
        var counter = 0
        if (equippedItems != null) {
            for(item in equippedItems){
                if (item != null) {
                    if(item.inventoryId == inventoryId){
                        return counter
                    }
                }
                counter++
            }
        }
        return null
    }

    private fun updateInventoryItemsLiveData(newList: List<InventoryItem>) {
        _equippedItems.postValue(_equippedItems.value)
        _items.postValue(newList)
    }

    companion object {
        const val FIRST_SLOT = 0
        const val SECOND_SLOT = 1
    }
}