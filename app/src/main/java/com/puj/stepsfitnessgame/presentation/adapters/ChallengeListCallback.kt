package com.puj.stepsfitnessgame.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

class ChallengeListCallback(
    private val oldList: List<Challenge>,
    private val newList: List<Challenge>
): DiffUtil.Callback(){

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].challengeName == newList[newItemPosition].challengeName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}