package com.puj.stepsfitnessgame.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemChallengeBinding
import com.puj.stepsfitnessgame.databinding.ItemChallengeNotStartedBinding
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

class ChallengeListAdapter: Adapter<ChallengeListAdapter.ChallengeListViewHolder>() {

    var challengeList: List<Challenge> = listOf()
        set(value) {
            val callback = ChallengeListCallback(challengeList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    private lateinit var context: Context

    var onItemIsShownListener: ((Challenge) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        val binding = when (viewType) {
            VIEW_TYPE_STARTED -> {
                ItemChallengeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            VIEW_TYPE_NOT_STARTED -> {
                ItemChallengeNotStartedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            else -> throw RuntimeException("Unknown view type^ $viewType")
        }
        context = parent.context
        return ChallengeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        val item = challengeList[position]
        when(holder.binding){
            is ItemChallengeBinding -> setOnChallengeStartedView(holder.binding, item)
            is ItemChallengeNotStartedBinding -> setOnChallengeNotStartedView(holder.binding, item)
        }
    }

    private fun setOnChallengeStartedView(
        binding: ItemChallengeBinding,
        item: Challenge
    ) {
        with(binding){
            tvChallengeName.text = item.challengeName
            tvPointsGained.text = context.getString(R.string.points_gained, item.pointsGained)
            tvGoal.text = context.getString(R.string.goal, item.goal)
            tvStepsTaken.text = context.getString(R.string.steps_taken, item.stepsTaken)
            tvTimeTillEnd.text = context.getString(R.string.time_till_end, item.timeTillEnd)
            pbChallengeProgress.progress = item.progress
            if(item.isShown){
                pbChallengeProgress.visibility = View.VISIBLE
                llChallengeInfoContainer.visibility = View.VISIBLE
            }
            setOnShowClickListener(ivShow, item)
        }
    }

    private fun setOnChallengeNotStartedView(
        binding: ItemChallengeNotStartedBinding,
        item: Challenge
    ) {
        with(binding){
            tvChallengeName.text = item.challengeName
            tvGoal.text = context.getString(R.string.goal, item.goal)
            tvTimeTillEnd.text = context.getString(R.string.time_till_end, item.timeTillEnd)
            if(item.isShown){
                llChallengeInfoContainer.visibility = View.VISIBLE
            }
            setOnShowClickListener(ivShow, item)
        }
    }

    private fun setOnShowClickListener(ivShow: ImageView, item: Challenge) {
        ivShow.setOnClickListener {
            onItemIsShownListener?.invoke(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = challengeList[position]
        return if(item.isStarted){
            VIEW_TYPE_STARTED
        }
        else{
            VIEW_TYPE_NOT_STARTED
        }
    }

    override fun getItemCount(): Int {
        return challengeList.size
    }

    class ChallengeListViewHolder(val binding: ViewBinding): ViewHolder(binding.root)

    companion object {

        const val VIEW_TYPE_NOT_STARTED = 100
        const val VIEW_TYPE_STARTED = 101

        const val MAX_POOL_SIZE = 30
    }
}