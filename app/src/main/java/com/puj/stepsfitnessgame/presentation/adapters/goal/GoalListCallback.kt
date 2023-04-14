package com.puj.stepsfitnessgame.presentation.adapters.goal

import androidx.recyclerview.widget.DiffUtil
import com.puj.stepsfitnessgame.domain.models.stepstatistics.Goal

class GoalListCallback(
    private val oldList: List<Goal>,
    private val newList: List<Goal>
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
        return oldItem.amountOfSteps == newItem.amountOfSteps
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}