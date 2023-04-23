package com.puj.stepsfitnessgame.presentation.adapters.guildparticipant

import androidx.recyclerview.widget.DiffUtil
import com.puj.stepsfitnessgame.domain.models.guild.GuildParticipant

class GuildParticipantCallback(
    private val oldList: List<GuildParticipant>,
    private val newList: List<GuildParticipant>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].participantId == newList[newItemPosition].participantId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}