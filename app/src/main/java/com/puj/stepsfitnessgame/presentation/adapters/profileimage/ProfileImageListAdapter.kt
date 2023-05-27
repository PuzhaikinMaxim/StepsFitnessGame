package com.puj.stepsfitnessgame.presentation.adapters.profileimage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemProfileImageBinding
import com.puj.stepsfitnessgame.domain.models.profileimage.ProfileImage

class ProfileImageListAdapter(
    private var context: Context?
): Adapter<ProfileImageListAdapter.ProfileImageListViewHolder>() {

    var profileImageList = listOf<ProfileImage>()
        set(value) {
            val callback = ProfileImageListCallback(field,value)
            val diff = DiffUtil.calculateDiff(callback)
            field = value
            diff.dispatchUpdatesTo(this)
        }

    var onSelectProfileImage: ((Int) -> (Unit))? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileImageListViewHolder {
        val profileImageBinding = ItemProfileImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProfileImageListViewHolder(profileImageBinding)
    }

    override fun onBindViewHolder(holder: ProfileImageListViewHolder, position: Int) {
        val item = profileImageList[position]
        with(holder.binding){
            if(item.isSelected){
                ivProfileImage.foreground = AppCompatResources.getDrawable(
                    context!!,
                    R.drawable.fg_item_user_image
                )
            }
            else{
                ivProfileImage.foreground = null
            }
            ivProfileImage.setImageResource(item.profileImageResourceId)
            ivProfileImage.setOnClickListener {
                onSelectProfileImage?.invoke(item.profileImageId)
            }
        }
    }

    override fun getItemCount(): Int {
        return profileImageList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class ProfileImageListViewHolder(
        val binding: ItemProfileImageBinding
        ): ViewHolder(binding.root)
}