package com.puj.stepsfitnessgame.presentation.adapters.guildparticipant

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemGuildParticipantBinding
import com.puj.stepsfitnessgame.domain.models.guild.GuildParticipant

class GuildParticipantAdapter(
    private val isGuildOwner: Boolean,
    private val playerProfileImage: TypedArray
): Adapter<GuildParticipantAdapter.GuildParticipantViewHolder>() {

    var guildParticipantList = listOf<GuildParticipant>()
        set(value) {
            val callback = GuildParticipantCallback(field,value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    private var context: Context? = null

    var onExpelParticipant: ((Long) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuildParticipantViewHolder {
        val binding = ItemGuildParticipantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return GuildParticipantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuildParticipantViewHolder, position: Int) {
        val item = guildParticipantList[position]
        with(holder.binding){
            tvParticipantLevel.text = context?.getString(
                R.string.participant_level,
                item.participantLevel
            )
            tvParticipantName.text = item.participantName
            if(!isGuildOwner){
                ivExpelUser.visibility = View.GONE
            }
            ivExpelUser.setOnClickListener {
                onExpelParticipant?.invoke(item.participantId)
            }
            ivParticipantIcon.setImageResource(
                playerProfileImage.getResourceId(item.participantProfileImageId, -1)
            )
        }
    }

    override fun getItemCount(): Int {
        return guildParticipantList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class GuildParticipantViewHolder(val binding: ItemGuildParticipantBinding): ViewHolder(binding.root)
}