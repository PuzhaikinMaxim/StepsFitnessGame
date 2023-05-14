package com.puj.stepsfitnessgame.presentation.adapters.rating

import androidx.recyclerview.widget.DiffUtil
import com.puj.stepsfitnessgame.domain.models.rating.Rating

class RatingCallback(
    private val oldList: List<Rating>,
    private val newList: List<Rating>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}