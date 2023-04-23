package com.puj.stepsfitnessgame.presentation.adapters.guildenterrequest

import androidx.recyclerview.widget.DiffUtil
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest

class GuildEnterRequestCallback(
    private val oldList: List<GuildEnterRequest>,
    private val newList: List<GuildEnterRequest>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].userId == newList[newItemPosition].userId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}