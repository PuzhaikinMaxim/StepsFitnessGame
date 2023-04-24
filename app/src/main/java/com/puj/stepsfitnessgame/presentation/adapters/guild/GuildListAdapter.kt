package com.puj.stepsfitnessgame.presentation.adapters.guild

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemGuildBinding
import com.puj.stepsfitnessgame.domain.models.guild.GuildListItem

class GuildListAdapter: Adapter<GuildListAdapter.GuildListViewHolder>() {

    var guildList = listOf<GuildListItem>()
        set(value) {
            val callback = GuildListCallback(field,value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var context: Context? = null

    var onJoinGuild: ((GuildListItem) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuildListViewHolder {
        val binding = ItemGuildBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return GuildListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuildListViewHolder, position: Int) {
        val item = guildList[position]
        with(holder.binding){
            tvGuildName.text = item.guildName
            tvGuildRank.text = context?.getString(R.string.guild_rank)
            if(item.isEnterRequested){
                btnJoinGuild.text = context?.getString(R.string.join_guild_cancel)
            }
            else{
                btnJoinGuild.text = context?.getString(R.string.join_guild_join)
            }
            tvGuildPlayersAmount.text = context?.getString(
                R.string.guild_players_amount_text, item.amountOfPlayers
            )
            onJoinGuild?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return guildList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class GuildListViewHolder(val binding: ItemGuildBinding): ViewHolder(binding.root)
}