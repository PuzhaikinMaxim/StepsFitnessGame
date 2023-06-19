package com.puj.stepsfitnessgame.presentation.activities

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ActivityInventoryBinding
import com.puj.stepsfitnessgame.databinding.ItemGameItemSmBinding
import com.puj.stepsfitnessgame.domain.models.item.InventoryItem
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.inventoryitems.InventoryItemsAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.InventoryViewModel

class InventoryActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityInventoryBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: InventoryViewModel

    private lateinit var itemImgIds: TypedArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences(
            PreferencesValues.PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[InventoryViewModel::class.java]
        setContentView(binding.root)
        itemImgIds = resources.obtainTypedArray(R.array.item_imgs)
        setRecyclerViewSpanCount()
        setupInventoryItemsRecyclerView()
        setupEquippedItems()
        setupModal()
        setupEquippedItemsCharacteristics()
    }

    private fun setRecyclerViewSpanCount() {
        val layoutManager = binding.rvItems.layoutManager
        if(layoutManager is GridLayoutManager){
            val displayMetrics = resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            layoutManager.spanCount = (dpWidth/90).toInt()
        }
    }

    private fun setupInventoryItemsRecyclerView() {
        val colorsList = resources.getStringArray(R.array.item_rarities_colors).asList()
        val adapter = InventoryItemsAdapter(colorsList, itemImgIds)
        adapter.onItemClickListener = {
            viewModel.selectItem(it)
        }
        viewModel.items.observe(this) {
            adapter.inventoryItemList = it
        }
        binding.rvItems.adapter = adapter
    }

    private fun setupEquippedItems() {
        viewModel.equippedItems.observe(this) {
            setupSlot(binding.equippedItem1, it[0], InventoryViewModel.FIRST_SLOT)
            binding.clEquippedItem1Container.setOnClickListener {
                viewModel.equipItem(InventoryViewModel.FIRST_SLOT)
            }
            setupSlot(binding.equippedItem2, it[1],  InventoryViewModel.SECOND_SLOT)
            binding.clEquippedItem2Container.setOnClickListener {
                viewModel.equipItem(InventoryViewModel.SECOND_SLOT)
            }
        }
    }

    private fun setupSlot(
        binding: ItemGameItemSmBinding,
        inventoryItem: InventoryItem?,
        slot: Int
    ) {
        if(inventoryItem == null) {
            binding.clContainer.visibility = View.INVISIBLE
            return
        }
        binding.tvItemName.text = inventoryItem.itemName
        binding.clContainer.visibility = View.VISIBLE
        binding.clContainer.setOnClickListener {
            viewModel.equipItem(slot)
        }
        val colorsList = resources.getStringArray(R.array.item_rarities_colors).asList()
        binding.ivItemImage.background.setTint(
            Color.parseColor(colorsList[inventoryItem.rarity-1])
        )
        if(inventoryItem.imageId != null){
            val image = itemImgIds.getResourceId(inventoryItem.imageId - 1, -1)
            if(image == -1) return
            binding.ivItemImage.setImageResource(image)
        }
    }

    private fun setupModal() {
        viewModel.selectedItem.observe(this){
            if(it == null){
                binding.lItemDescription.clModalContainer.visibility = View.GONE
            }
            else {
                resetAllButtons()
                binding.lItemDescription.clModalContainer.visibility = View.VISIBLE
                binding.lItemDescription.tvItemName.text = it.itemName
                setModalCharacteristics(it)
                with(binding.lItemDescription){
                    btnToSlot1.setOnClickListener {
                        viewModel.equipItem(InventoryViewModel.FIRST_SLOT)
                    }
                    btnToSlot2.setOnClickListener {
                        viewModel.equipItem(InventoryViewModel.SECOND_SLOT)
                    }
                }
                disableSlotButtonsIfItemAlreadyEquipped(it)
            }
        }

        binding.lItemDescription.icClose.setOnClickListener {
            viewModel.unselectItem()
        }
    }

    private fun disableSlotButtonsIfItemAlreadyEquipped(inventoryItem: InventoryItem) {
        val slot = viewModel.getEquipmentSlot(inventoryItem.inventoryId) ?: return
        with(binding.lItemDescription){
            btnToSlot1.visibility = View.GONE
            btnToSlot2.visibility = View.GONE
            btnRemoveFromInventory.visibility = View.VISIBLE
            btnRemoveFromInventory.setOnClickListener {
                viewModel.unequipItem(slot)
            }
        }
    }

    private fun resetAllButtons() {
        with(binding.lItemDescription){
            btnToSlot1.visibility = View.VISIBLE
            btnToSlot2.visibility = View.VISIBLE
            btnRemoveFromInventory.visibility = View.GONE
        }
    }

    private fun setModalCharacteristics(
        item: InventoryItem
    ) {
        with(binding.lItemDescription){
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

    private fun setupEquippedItemsCharacteristics() {
        viewModel.equippedItemsCharacteristics.observe(this){
            with(binding){
                tvAmountOfMinutesFixed.text = getString(
                    R.string.amount_of_minutes_fixed,
                    it.plusMinutes
                )
                tvTimeMultiplier.text = getString(
                    R.string.time_multiplier,
                    it.timeMultiplier.toString()
                )
                tvAmountOfPointsFixed.text = getString(
                    R.string.amount_of_points_fixed,
                    it.pointsFixed
                )
                tvAmountOfPointsMultiplier.text = getString(
                    R.string.amount_of_points_multiplier,
                    it.pointsMultiplier.toString()
                )
            }
        }
    }

    private fun<T> setItemCharacteristicsTextView(
        tv: TextView, resId: Int, value: T, comparator: () -> Boolean
    ){
        tv.text = if(comparator.invoke()){
            getString(resId, value)
        }
        else{
            ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        itemImgIds.recycle()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, InventoryActivity::class.java)
        }
    }
}