package com.puj.stepsfitnessgame.presentation.adapters.guildlogo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemGuildLogoBinding
import com.puj.stepsfitnessgame.domain.models.guild.GuildLogo

class GuildLogoAdapter: Adapter<GuildLogoAdapter.GuildLogoViewHolder>() {

    var guildLogoList = listOf<GuildLogo>()
        set(value) {
            val guildLogoCallback = GuildLogoCallback(field,value)
            val diffResult = DiffUtil.calculateDiff(guildLogoCallback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    private var context: Context? = null

    var onSelectGuildLogo: ((Int) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuildLogoViewHolder {
        val binding = ItemGuildLogoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GuildLogoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuildLogoViewHolder, position: Int) {
        val item = guildLogoList[position]
        with(holder.binding){
            if(item.isSelected){
                ivGuildLogo.foreground = AppCompatResources.getDrawable(
                    context!!,
                    R.drawable.fg_item_guild_logo
                )
            }
            else{
                ivGuildLogo.foreground = null
            }
            ivGuildLogo.setImageResource(item.guildLogoResourceId)
            ivGuildLogo.setOnClickListener {
                onSelectGuildLogo?.invoke(item.guildLogoId)
            }
        }
    }

    override fun getItemCount(): Int {
        return guildLogoList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class GuildLogoViewHolder(val binding: ItemGuildLogoBinding): ViewHolder(binding.root)
}