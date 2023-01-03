package com.puj.stepsfitnessgame.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.databinding.ItemChallengeBinding
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

class ChallengeListAdapter: Adapter<ChallengeListAdapter.ChallengeListViewHolder>() {

    var challengeList: List<Challenge> = listOf()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ChallengeListViewHolder(val binding: ItemChallengeBinding): ViewHolder(binding.root)
}