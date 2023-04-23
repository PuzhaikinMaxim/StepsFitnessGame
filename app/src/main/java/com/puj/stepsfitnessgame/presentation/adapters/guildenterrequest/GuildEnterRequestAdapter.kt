package com.puj.stepsfitnessgame.presentation.adapters.guildenterrequest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemRequestingPlayerBinding
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest

class GuildEnterRequestAdapter: Adapter<GuildEnterRequestAdapter.GuildEnterRequestViewHolder>() {

    var guildEnterRequestList = listOf<GuildEnterRequest>()
        set(value) {
            val callback = GuildEnterRequestCallback(field,value)
            val diff = DiffUtil.calculateDiff(callback)
            diff.dispatchUpdatesTo(this)
            field = value
        }

    private var context: Context? = null

    private var onRefuseEnter: ((Long) -> (Unit))? = null

    private var onAcceptEnter: ((Long) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuildEnterRequestViewHolder {
        val binding = ItemRequestingPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return GuildEnterRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuildEnterRequestViewHolder, position: Int) {
        val item = guildEnterRequestList[position]
        with(holder.binding){
            tvPlayerLevel.text = context?.getString(R.string.user_level_text, item.userLevel)
            tvPlayerName.text = item.userName
            ivAcceptRequest.setOnClickListener {
                onAcceptEnter?.invoke(item.userId)
            }
            ivRefuseRequest.setOnClickListener {
                onRefuseEnter?.invoke(item.userId)
            }
        }
    }

    override fun getItemCount(): Int {
        return guildEnterRequestList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class GuildEnterRequestViewHolder(
        val binding: ItemRequestingPlayerBinding
        ): ViewHolder(binding.root)
}