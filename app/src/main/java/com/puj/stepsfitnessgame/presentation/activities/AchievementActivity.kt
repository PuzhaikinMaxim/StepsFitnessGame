package com.puj.stepsfitnessgame.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.databinding.ActivityAchievementsBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.achievement.AchievementListAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.AchievementViewModel

class AchievementActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityAchievementsBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: AchievementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val sharedPref = getSharedPreferences(
            PreferencesValues.PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        viewModel = ViewModelProvider(
            this,ViewModelFactory(sharedPref)
        )[AchievementViewModel::class.java]
        setupAchievementList()
    }

    private fun setupAchievementList() {
        val stepAchievementListAdapter = AchievementListAdapter()
        val challengeAchievementListAdapter = AchievementListAdapter()
        val duelsAchievementListAdapter = AchievementListAdapter()

        viewModel.achievementList.observe(this){
            stepAchievementListAdapter.achievementList = viewModel.stepAchievementsList
            challengeAchievementListAdapter.achievementList = viewModel.challengeAchievementsList
            duelsAchievementListAdapter.achievementList = viewModel.duelAchievementsList
        }

        binding.rvStepsAchievements.adapter = stepAchievementListAdapter
        binding.rvChallengesAchievements.adapter = challengeAchievementListAdapter
        binding.rvDuelsAchievements.adapter = duelsAchievementListAdapter
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, AchievementActivity::class.java)
        }
    }
}