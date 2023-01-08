package com.puj.stepsfitnessgame.presentation.adapters

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ItemChallengeBinding
import com.puj.stepsfitnessgame.domain.models.challenge.Challenge

class ChallengeListAdapter: Adapter<ChallengeListAdapter.ChallengeListViewHolder>() {

    var challengeList: List<Challenge> = listOf()
        set(value) {
            val callback = ChallengeListCallback(challengeList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    private var isIvShowAnimationEnded = true

    private lateinit var context: Context

    var onItemIsShownListener: ((Challenge) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        val binding = ItemChallengeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        context = parent.context
        return ChallengeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        val item = challengeList[position]
        with(holder.binding){
            tvChallengeName.text = item.challengeName
            tvPointsGained.text = context.getString(R.string.points_gained, item.pointsGained)
            tvGoal.text = context.getString(R.string.goal, item.goal)
            tvStepsTaken.text = context.getString(R.string.steps_taken, item.stepsTaken)
            tvTimeTillEnd.text = context.getString(R.string.time_till_end, item.timeTillEnd)
            pbChallengeProgress.progress = item.progress
            when(item.isShown){
                true -> {
                    pbChallengeProgress.visibility = View.VISIBLE
                    llChallengeInfoContainer.visibility = View.VISIBLE
                }
                false -> {
                    pbChallengeProgress.visibility = View.GONE
                    llChallengeInfoContainer.visibility = View.GONE
                }
            }
            setOnShowClickListener(ivShow, item)
        }
    }

    private fun setOnShowClickListener(ivShow: ImageView, item: Challenge) {
        ivShow.setOnClickListener {
            if(isIvShowAnimationEnded){
                onItemIsShownListener?.invoke(item)
                startShowButtonOnClickAnimation(ivShow)
                isIvShowAnimationEnded = false
            }
        }
    }

    private fun startShowButtonOnClickAnimation(ivShow: ImageView) {
        val prevRotation = ivShow.rotation
        val animator = ObjectAnimator.ofFloat(
            ivShow,
            "rotation",
            prevRotation,
            prevRotation + ROTATION_ANGLE
        )

        addAnimationListener(animator)

        animator.setDuration(ANIMATION_DURATION).start()
    }

    private fun addAnimationListener(animator: ObjectAnimator) {
        val animationListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                isIvShowAnimationEnded = true
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }
        }

        animator.addListener(animationListener)
    }

    override fun getItemCount(): Int {
        return challengeList.size
    }

    class ChallengeListViewHolder(val binding: ItemChallengeBinding): ViewHolder(binding.root)

    companion object {
        private const val ROTATION_ANGLE = 180
        private const val ANIMATION_DURATION = 500L
    }
}