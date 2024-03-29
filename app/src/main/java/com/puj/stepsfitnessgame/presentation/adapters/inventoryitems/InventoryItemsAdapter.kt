package com.puj.stepsfitnessgame.presentation.adapters.inventoryitems

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemGameItemSmBinding
import com.puj.stepsfitnessgame.domain.models.item.InventoryItem

class InventoryItemsAdapter(
    private val colorCodesList: List<String>,
    private val itemImgIds: TypedArray
): Adapter<InventoryItemsAdapter.InventoryItemViewHolder>() {

    var inventoryItemList = listOf<InventoryItem>()
        set(value) {
            val callback = InventoryItemsCallback(field,value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onItemClickListener: ((Int) -> Unit)? = null

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryItemViewHolder {
        val binding = ItemGameItemSmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return InventoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InventoryItemViewHolder, position: Int) {
        val item = inventoryItemList[position]
        with(holder.binding){
            tvItemName.text = item.itemName
            clContainer.setOnClickListener {
                onItemClickListener?.invoke(item.inventoryId)
            }
            ivItemImage.background.setTint(Color.parseColor(colorCodesList[item.rarity-1]))
            if(item.isEquipped){
                cvItemContainer.foreground =
                    AppCompatResources.getDrawable(context!!, R.drawable.bc_inventory_item_equiped)
                cvItemContainer.setCardBackgroundColor(context!!.getColor(R.color.item_light_red))
            }
            else{
                cvItemContainer.foreground = null
                cvItemContainer.setCardBackgroundColor(context!!.getColor(R.color.item_light_orange))
            }
            if(item.imageId != null){
                val image = itemImgIds.getResourceId(item.imageId - 1, -1)
                if(image == -1) return
                ivItemImage.setImageResource(image)
            }
        }
    }

    override fun getItemCount(): Int {
        return inventoryItemList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class InventoryItemViewHolder(val binding: ItemGameItemSmBinding): ViewHolder(binding.root)
}