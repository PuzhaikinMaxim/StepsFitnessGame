package com.puj.stepsfitnessgame.presentation.adapters.inventoryitems

import androidx.recyclerview.widget.DiffUtil
import com.puj.stepsfitnessgame.domain.models.item.InventoryItem

class InventoryItemsCallback(
    private val oldList: List<InventoryItem>,
    private val newList: List<InventoryItem>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.inventoryId == newItem.inventoryId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}