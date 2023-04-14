package com.puj.stepsfitnessgame.presentation.adapters.goal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemGoalBinding
import com.puj.stepsfitnessgame.domain.models.stepstatistics.Goal

class GoalListAdapter: Adapter<GoalListAdapter.GoalItemViewHolder>() {

    var goalList: List<Goal> = listOf()
        set(value) {
            val callback = GoalListCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    private lateinit var context: Context

    lateinit var onGoalPressedCallback: (Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalItemViewHolder {
        val item = ItemGoalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        context = parent.context

        return GoalItemViewHolder(item)
    }

    override fun onBindViewHolder(holder: GoalItemViewHolder, position: Int) {
        val item = goalList[position]
        with(holder.binding){
            tvGoal.text = context.getString(
                R.string.item_goal_selection_text,
                item.amountOfSteps,
                item.estimatedKilometers
            )
            when(item.isSelected){
                true -> ivGoalSet.visibility = View.VISIBLE
                false -> ivGoalSet.visibility = View.GONE
            }
            clItemGoalContainer.setOnClickListener {
                onGoalPressedCallback.invoke(item.amountOfSteps)
            }
        }
    }

    override fun getItemCount(): Int {
        return goalList.size
    }

    class GoalItemViewHolder(val binding: ItemGoalBinding): ViewHolder(binding.root)
}