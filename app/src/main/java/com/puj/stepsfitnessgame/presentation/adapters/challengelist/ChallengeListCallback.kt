package com.puj.stepsfitnessgame.presentation.adapters.challengelist

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
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when {
            !oldItem.isShown && newItem.isShown -> ChallengeListItemAnimator.OPENED
            oldItem.isShown && !newItem.isShown -> ChallengeListItemAnimator.CLOSED
            else -> null
        }
    }
}