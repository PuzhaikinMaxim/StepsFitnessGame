package com.puj.stepsfitnessgame.presentation.adapters.profileimage

import androidx.recyclerview.widget.DiffUtil
import com.puj.stepsfitnessgame.domain.models.profileimage.ProfileImage

class ProfileImageListCallback(
    private val oldList: List<ProfileImage>,
    private val newList: List<ProfileImage>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].profileImageId == newList[newItemPosition].profileImageId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}