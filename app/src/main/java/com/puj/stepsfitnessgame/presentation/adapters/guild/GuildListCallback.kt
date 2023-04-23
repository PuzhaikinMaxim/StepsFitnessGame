package com.puj.stepsfitnessgame.presentation.adapters.guild

import androidx.recyclerview.widget.DiffUtil
import com.puj.stepsfitnessgame.domain.models.guild.GuildListItem

class GuildListCallback(
    private val oldList: List<GuildListItem>,
    private val newList: List<GuildListItem>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].guildId == newList[newItemPosition].guildId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}