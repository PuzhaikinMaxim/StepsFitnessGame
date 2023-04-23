package com.puj.stepsfitnessgame.presentation.adapters.guildlogo

import androidx.recyclerview.widget.DiffUtil
import com.puj.stepsfitnessgame.domain.models.guild.GuildLogo

class GuildLogoCallback(
    private val oldList: List<GuildLogo>,
    private val newList: List<GuildLogo>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].guildLogoId == newList[newItemPosition].guildLogoId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}