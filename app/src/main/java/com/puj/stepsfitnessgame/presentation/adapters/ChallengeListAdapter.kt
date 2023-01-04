package com.puj.stepsfitnessgame.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemChallengeBinding
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

class ChallengeListAdapter: Adapter<ChallengeListAdapter.ChallengeListViewHolder>() {

    var challengeList: List<Challenge> = listOf()
        set(value) {
            val callback = ChallengeListCallback(field, value)
            val diff = DiffUtil.calculateDiff(callback)
            diff.dispatchUpdatesTo(this)
            field = value
        }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        val binding = ItemChallengeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return ChallengeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        val item = challengeList[position]
        with(holder.binding){
            tvChallengeName.text = item.challengeName
            tvPointsGained.text = context.getString(R.string.points_gained, item.pointsGained)
            tvGoal.text = context.getString(R.string.goal, item.goal)
            tvStepsTaken.text = context.getString(R.string.steps_taken, item.stepsTaken)
            tvTimeTillEnd.text = context.getString(R.string.time_till_end, item.timeTillEnd)
            pbChallengeProgress.progress = item.progress
        }
    }

    override fun getItemCount(): Int {
        return challengeList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    class ChallengeListViewHolder(val binding: ItemChallengeBinding): ViewHolder(binding.root)
}