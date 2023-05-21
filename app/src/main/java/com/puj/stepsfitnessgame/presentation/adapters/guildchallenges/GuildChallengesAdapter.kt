package com.puj.stepsfitnessgame.presentation.adapters.guildchallenges

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemGuildChallengeBinding
import com.puj.stepsfitnessgame.domain.models.guild.GuildChallenge

class GuildChallengesAdapter(): Adapter<GuildChallengesAdapter.GuildChallengesViewHolder>() {

    var challengesList = listOf<GuildChallenge>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var context: Context? = null

    var onStartButtonClick: ((Long) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuildChallengesViewHolder {
        val binding = ItemGuildChallengeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return GuildChallengesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuildChallengesViewHolder, position: Int) {
        val item = challengesList[position]
        with(holder.binding){
            tvGoal.text = context?.getString(R.string.goal, item.goal)
            tvTimeTillEnd.text = context?.getString(R.string.time_till_end, item.hoursToFinishString)
            btnStartChallenge.setOnClickListener {
                onStartButtonClick?.invoke(item.challengeId)
            }
        }
    }

    override fun getItemCount(): Int {
        return challengesList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class GuildChallengesViewHolder(val binding: ItemGuildChallengeBinding): ViewHolder(binding.root)
}