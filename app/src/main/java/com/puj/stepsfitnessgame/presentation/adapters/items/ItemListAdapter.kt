package com.puj.stepsfitnessgame.presentation.adapters.items

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemGameItemBinding
import com.puj.stepsfitnessgame.domain.models.item.Item

class ItemListAdapter: Adapter<ItemListAdapter.ItemListViewHolder>() {

    var itemsList: List<Item> = listOf()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        notifyDataSetChanged()
        field = value
    }

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val binding = ItemGameItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return ItemListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val item = itemsList[position]

        with(holder.binding){
            tvItemName.text = item.itemName
            setItemCharacteristicsFixedTextView(
                tvAmountOfMinutesFixed,
                R.string.amount_of_minutes_fixed,
                item.plusMinutes
            )
            setItemCharacteristicsMultiplierTextView(
                tvTimeMultiplier,
                R.string.time_multiplier,
                item.timeMultiplier
            )
            setItemCharacteristicsFixedTextView(
                tvAmountOfPointsFixed,
                R.string.amount_of_points_fixed,
                item.pointsFixed
            )
            setItemCharacteristicsMultiplierTextView(
                tvAmountOfPointsMultiplier,
                R.string.amount_of_points_multiplier,
                item.pointsMultiplier
            )
        }
    }

    private fun setItemCharacteristicsFixedTextView(
        tv: TextView, resId: Int, value: Int
    ){
        tv.text = if(value != 0){
            context?.getString(resId, value)
        }
        else{
            ""
        }
    }

    private fun setItemCharacteristicsMultiplierTextView(
        tv: TextView, resId: Int, value: Double
    ){
        tv.text = if(value != 0.0){
            context?.getString(resId, value.toString())
        }
        else{
            ""
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class ItemListViewHolder(val binding: ItemGameItemBinding): ViewHolder(binding.root)
}