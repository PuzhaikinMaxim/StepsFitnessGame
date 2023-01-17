package com.puj.stepsfitnessgame.presentation.adapters

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.puj.stepsfitnessgame.databinding.ItemChallengeBinding
import com.puj.stepsfitnessgame.databinding.ItemChallengeNotStartedBinding

class ChallengeListItemAnimator: DefaultItemAnimator() {

    private var isIvShowAnimationEnded = true

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {

        val holder = newHolder as ChallengeListAdapter.ChallengeListViewHolder

        when(holder.binding){
            is ItemChallengeBinding -> {
                with(holder.binding){
                    if(preInfo is ChallengeItemHolderInfo){
                        if(preInfo.isOpened){
                            setupShowButtonAnimation(ivShow, holder)
                            pbChallengeProgress.visibility = View.VISIBLE
                            llChallengeInfoContainer.visibility = View.VISIBLE
                        }
                        else {
                            setupShowButtonAnimation(ivShow, holder)
                            startSlideAnimation(
                                clChallengeItemBackground,
                                llChallengeNameContainer,
                                pbChallengeProgress,
                                llChallengeInfoContainer
                            )
                        }
                        return true
                    }
                }
            }
            is ItemChallengeNotStartedBinding -> {
                with(holder.binding){
                    if(preInfo is ChallengeItemHolderInfo){
                        println(preInfo.isOpened)
                        if(preInfo.isOpened){
                            setupShowButtonAnimation(ivShow, holder)
                            llChallengeInfoContainer.visibility = View.VISIBLE
                        }
                        else {
                            setupShowButtonAnimation(ivShow, holder)
                            startSlideAnimationForNotStartedItem(
                                clChallengeItemBackground,
                                llChallengeNameContainer,
                                llChallengeInfoContainer
                            )
                        }
                        return true
                    }
                }
            }
        }

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }

    private fun startSlideAnimationForNotStartedItem(
        clChallengeItemBackground: ConstraintLayout,
        llChallengeNameContainer: LinearLayout,
        llChallengeInfoContainer: LinearLayout) {
        val toHeight =
            llChallengeNameContainer.height +
                    clChallengeItemBackground.paddingTop +
                    clChallengeItemBackground.paddingBottom

        val fromHeight = clChallengeItemBackground.height

        val animationUpdateListener = AnimatorUpdateListener {
            val animatedValue = it.animatedValue as Int
            clChallengeItemBackground.layoutParams.height = animatedValue
            clChallengeItemBackground.requestLayout()
        }

        setupAlphaAnimation(llChallengeInfoContainer)

        val animationListener = object : AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                llChallengeInfoContainer.visibility = View.GONE
                clChallengeItemBackground.layoutParams.height = LayoutParams.WRAP_CONTENT
                clChallengeItemBackground.requestLayout()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }

        }

        val slideUpAnimator = ValueAnimator.ofInt(fromHeight, toHeight)
            .setDuration(ANIMATION_DURATION)
        slideUpAnimator.addUpdateListener(animationUpdateListener)
        slideUpAnimator.addListener(animationListener)
        slideUpAnimator.start()
    }

    private fun startSlideAnimation(
        clChallengeItemBackground: ConstraintLayout,
        llChallengeNameContainer: LinearLayout,
        pbChallengeProgress: ProgressBar,
        llChallengeInfoContainer: LinearLayout
    ) {
        val toHeight =
            llChallengeNameContainer.height +
                    clChallengeItemBackground.paddingTop +
                    clChallengeItemBackground.paddingBottom

        val fromHeight = clChallengeItemBackground.height

        val animationUpdateListener = AnimatorUpdateListener {
            val animatedValue = it.animatedValue as Int
            clChallengeItemBackground.layoutParams.height = animatedValue
            clChallengeItemBackground.requestLayout()
        }

        setupAlphaAnimation(pbChallengeProgress)
        setupAlphaAnimation(llChallengeInfoContainer)

        val animationListener = object : AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                pbChallengeProgress.visibility = View.GONE
                llChallengeInfoContainer.visibility = View.GONE
                clChallengeItemBackground.layoutParams.height = LayoutParams.WRAP_CONTENT
                clChallengeItemBackground.requestLayout()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }

        }

        val slideUpAnimator = ValueAnimator.ofInt(fromHeight, toHeight)
                .setDuration(ANIMATION_DURATION)
        slideUpAnimator.addUpdateListener(animationUpdateListener)
        slideUpAnimator.addListener(animationListener)
        slideUpAnimator.start()
    }

    private fun setupAlphaAnimation(view: View) {
        val animation = AlphaAnimation(1.0f, 0.0f)

        val animationListener = object : AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                view.alpha = 1.0f
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        }

        animation.setAnimationListener(animationListener)
        animation.duration = ANIMATION_DURATION

        view.startAnimation(animation)
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder, payloads: MutableList<Any>): Boolean {
        return true
    }

    private fun setupShowButtonAnimation(
        ivShow: ImageView,
        holder: ChallengeListAdapter.ChallengeListViewHolder
    ) {
        ivShow.isClickable = false
        startShowButtonOnClickAnimation(ivShow, holder)
    }

    private fun startShowButtonOnClickAnimation(
        ivShow: ImageView,
        holder: ChallengeListAdapter.ChallengeListViewHolder
    ) {
        val prevRotation = ivShow.rotation
        val animator = ObjectAnimator.ofFloat(
            ivShow,
            "rotation",
            prevRotation,
            prevRotation + ROTATION_ANGLE
        )

        addAnimationListener(animator, holder)

        animator.setDuration(ANIMATION_DURATION).start()
    }

    private fun addAnimationListener(
        animator: ObjectAnimator,
        holder: ChallengeListAdapter.ChallengeListViewHolder
    ) {
        val animationListener = object : AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                if(holder.binding is ItemChallengeBinding) {
                    holder.binding.ivShow.isClickable = true
                    isIvShowAnimationEnded = true
                    dispatchAnimationFinished(holder)
                }
                if(holder.binding is ItemChallengeNotStartedBinding) {
                    holder.binding.ivShow.isClickable = true
                    isIvShowAnimationEnded = true
                    dispatchAnimationFinished(holder)
                }
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }
        }

        animator.addListener(animationListener)
    }

    class ChallengeItemHolderInfo(val isOpened: Boolean) : ItemHolderInfo()

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {

        if(changeFlags == FLAG_CHANGED){
            for(payload in payloads){
                if(payload as? Int == OPENED){
                    return ChallengeItemHolderInfo(true)
                }
                else if(payload as? Int == CLOSED){
                    return ChallengeItemHolderInfo(false)
                }
            }
        }

        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

    companion object {
        const val OPENED = 0
        const val CLOSED = 1

        private const val ROTATION_ANGLE = 180
        private const val ANIMATION_DURATION = 500L
    }
}