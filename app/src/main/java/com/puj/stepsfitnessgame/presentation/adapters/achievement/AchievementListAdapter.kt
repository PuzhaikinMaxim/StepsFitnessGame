package com.puj.stepsfitnessgame.presentation.adapters.achievement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemAchievementBinding
import com.puj.stepsfitnessgame.domain.models.achievement.Achievement

class AchievementListAdapter: Adapter<AchievementListAdapter.AchievementViewHolder>() {

    var achievementList = listOf<Achievement>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val binding = ItemAchievementBinding.inflate(
            LayoutInflater.from(
            parent.context
        ), parent, false)
        context = parent.context
        return AchievementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val item = achievementList[position]
        with(holder.binding){
            tvAchievementName.text = item.achievementName
            tvAchievementDescription.text = item.achievementDescription
            setAchievementIcon(item, ivAchievementIcon)
            setAchievementCompletion(item, holder)
        }
    }

    private fun setAchievementIcon(achievement: Achievement, imageView: ImageView) {
        when(achievement.achievementType){
            Achievement.achievementTypeStep -> {
                imageView.setImageResource(R.drawable.ic_achievement_steps)
            }
            Achievement.achievementTypeChallenge -> {
                imageView.setImageResource(R.drawable.ic_achievement_challenge)
            }
            Achievement.achievementTypeDuel -> {
                imageView.setImageResource(R.drawable.ic_achievement_duel)
            }
        }
    }

    private fun setAchievementCompletion(achievement: Achievement, holder: AchievementViewHolder) {
        with(holder.binding){
            when(achievement.isCompleted){
                true -> {
                    ivLock.visibility = View.GONE
                    ivAchievementIcon.foreground = null
                }
                false -> {
                    ivLock.visibility = View.VISIBLE
                    ivAchievementIcon.foreground =
                        AppCompatResources.getDrawable(context!!, R.drawable.fg_achievement_icon)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return achievementList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class AchievementViewHolder(val binding: ItemAchievementBinding): ViewHolder(binding.root)
}