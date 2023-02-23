package com.puj.stepsfitnessgame.presentation.adapters.levellist

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemLevelBinding
import com.puj.stepsfitnessgame.domain.models.dungeonlevel.DungeonLevel

class LevelAdapter(): Adapter<LevelAdapter.LevelViewHolder>() {

    var levelList: List<DungeonLevel> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    var onLevelClickListener: ((level: DungeonLevel) -> Unit)? = null

    lateinit var levelNames: ArrayList<String>

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val binding = ItemLevelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        levelNames = ArrayList(parent.context.resources.getStringArray(
            R.array.item_level_dungeon_level_names
        ).toList())
        context = parent.context
        return LevelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val level = levelList[position]

        with(holder.binding){
            val levelName = levelNames[level.dungeonLevel - 1]
            tvLevelName.text = context?.getString(
                R.string.item_level_name_text,
                level.dungeonLevel,
                levelName
            ) ?: ""
            if(level.isLocked){
                tvAmountOfChallenges.visibility = View.GONE
                tvMinimalLevelRequirements.text = context?.getString(
                    R.string.item_level_minimal_level_requirement_text,
                    level.minimalLevelRequirements
                ) ?: ""
            }
            else {
                ivLockIcon.visibility = View.GONE
                tvMinimalLevelRequirements.visibility = View.GONE
                tvAmountOfChallenges.text = context?.getString(
                    R.string.item_level_amount_of_challenges_completed,
                    level.amountOfCompletedChallenges,
                    level.amountOfChallenges
                ) ?: ""
                ivLevelBackground.setOnClickListener {
                    onLevelClickListener?.invoke(level)
                }

            }
            setLevelBackground(level.dungeonLevel, ivLevelBackground)
        }
    }

    private fun setLevelBackground(level: Int, backgroundImage: ImageView) {
        if(context != null) {
            val image = when(level){
                1 -> {
                    context!!.resources.getDrawable(R.drawable.level_1)
                }
                2 -> {
                    context!!.resources.getDrawable(R.drawable.level_2)
                }
                3 -> {
                    context!!.resources.getDrawable(R.drawable.level_3)
                }
                4 -> {
                    context!!.resources.getDrawable(R.drawable.level_4)
                }
                5 -> {
                    context!!.resources.getDrawable(R.drawable.level_5)
                }
                else -> {
                    throw RuntimeException("Level not matching")
                }
            }
            backgroundImage.setImageDrawable(image)
        }
    }

    override fun getItemCount(): Int {
        return levelList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class LevelViewHolder(val binding: ItemLevelBinding): ViewHolder(binding.root)
}