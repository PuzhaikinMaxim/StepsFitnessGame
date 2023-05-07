package com.puj.stepsfitnessgame.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ActivityUserProfileBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.UserProfileViewModel

class UserProfileActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityUserProfileBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: UserProfileViewModel

    private val guildLogoIds = resources.obtainTypedArray(R.array.guild_logos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val sharedPref = getSharedPreferences(
            PreferencesValues.PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        viewModel = ViewModelProvider(this,ViewModelFactory(sharedPref))[
                UserProfileViewModel::class.java
        ]
        setupUserProfileData()
    }

    private fun setupUserProfileData() {
        viewModel.userProfileData.observe(this){
            with(binding){
                tvUserName.text = it.userData.username
                tvLevel.text = it.userData.level.toString()
                tvAmountOfAchievements.text = getString(
                    R.string.profile_amount_of_achievements,
                    it.amountOfAchievements
                )
                tvAmountOfCompletedChallenges.text = getString(
                    R.string.profile_amount_of_completed_challenges,
                    it.amountOfCompletedChallenges
                )
                tvAmountOfSteps.text = getString(
                    R.string.profile_amount_of_steps,
                    it.amountOfSteps
                )
                tvDuelsWon.text = getString(
                    R.string.profile_duels_won,
                    it.duelsWon
                )
                if(it.guildData != null){
                    tvGuildName.text = it.guildData.guildName
                    ivGuildLogo.setImageResource(guildLogoIds.getResourceId(
                        it.guildData.guildLogoId, 0
                    ))
                }
                else{
                    llGuildContainer.visibility = View.GONE
                }
            }
        }

    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, UserProfileActivity::class.java)
        }
    }
}