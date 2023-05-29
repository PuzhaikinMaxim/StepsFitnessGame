package com.puj.stepsfitnessgame.presentation.adapters.rating

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemRatingBinding
import com.puj.stepsfitnessgame.domain.models.rating.Rating

class RatingAdapter(var context: Context?): Adapter<RatingAdapter.ItemRatingViewHolder>() {

    var ratingList = listOf<Rating>()
        set(value) {
            val callback = RatingCallback(field,value)
            val diffResult = DiffUtil.calculateDiff(callback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    var playerProfileImage = context!!.resources.obtainTypedArray(R.array.profile_images)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRatingViewHolder {
        val binding = ItemRatingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemRatingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemRatingViewHolder, position: Int) {
        val item = ratingList[position]
        with(holder.binding){
            tvPlayerName.text = item.playerName
            tvPlayerLevel.text = context!!.getString(R.string.user_level_text, item.playerLevel)
            ivPlayerIcon.setImageResource(playerProfileImage.getResourceId(item.profileImageId, -1))
            if(item.place != null){
                tvPlayerPlace.text = context!!.getString(R.string.tv_player_place_text, item.place)
            }
            when(item.ratingType){
                Rating.RatingType.TYPE_STEPS -> {
                    tvPlayerRatingStat.text = context!!.getString(
                        R.string.player_rating_stat_steps_text,
                        item.amountOfSteps
                    )
                }
                Rating.RatingType.TYPE_DUELS -> {
                    tvPlayerRatingStat.text = context!!.getString(
                        R.string.player_rating_stat_duels_text,
                        item.amountOfDuelsWon
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return ratingList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
        playerProfileImage.recycle()
    }

    class ItemRatingViewHolder(val binding: ItemRatingBinding): ViewHolder(binding.root)
}