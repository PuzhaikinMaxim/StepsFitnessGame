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
import com.puj.stepsfitnessgame.databinding.ItemGameItemAltBinding
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
        val binding = ItemGameItemAltBinding.inflate(
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
            setItemCharacteristicsTextView(
                tvAmountOfMinutesFixed,
                R.string.amount_of_minutes_fixed,
                item.plusMinutes
            ){item.plusMinutes != 0}
            setItemCharacteristicsTextView(
                tvTimeMultiplier,
                R.string.time_multiplier,
                item.timeMultiplier.toString()
            ){item.timeMultiplier != 0.0}
            setItemCharacteristicsTextView(
                tvAmountOfPointsFixed,
                R.string.amount_of_points_fixed,
                item.pointsFixed
            ){item.pointsFixed != 0}
            setItemCharacteristicsTextView(
                tvAmountOfPointsMultiplier,
                R.string.amount_of_points_multiplier,
                item.pointsMultiplier.toString()
            ){item.pointsMultiplier != 0.0}
        }
    }

    private fun<T> setItemCharacteristicsTextView(
        tv: TextView, resId: Int, value: T, comparator: () -> Boolean
    ){
        tv.text = if(comparator.invoke()){
            context?.getString(resId, value)
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

    class ItemListViewHolder(val binding: ItemGameItemAltBinding): ViewHolder(binding.root)
}