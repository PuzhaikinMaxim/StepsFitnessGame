package com.puj.stepsfitnessgame.presentation.activities

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ActivityCharacterisitcsBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.CharacteristicsViewModel

class CharacteristicsActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityCharacterisitcsBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: CharacteristicsViewModel

    private lateinit var userProfileImages: TypedArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val sharedPref = getSharedPreferences(
            PreferencesValues.PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[CharacteristicsViewModel::class.java]
        userProfileImages = resources.obtainTypedArray(R.array.profile_images)
        observeCharacteristics()
        setupOnButtonEnduranceClickListener()
        setupOnButtonStrengthClickListener()
        setupUserData()
        setupCanPressButtons()
    }

    private fun observeCharacteristics() {
        viewModel.characteristics.observe(this){
            binding.tvStrengthPoints.text = it.strength.toString()
            binding.tvEndurancePoints.text = it.endurance.toString()
            binding.tvPointsMultiplier.text = getString(R.string.plus_points_multiplier, it.strength)
            binding.tvTimeMultiplier.text = getString(R.string.plus_time_multiplier, it.endurance)
            binding.tvUnassignedPoints.text = getString(R.string.unassigned_points, it.unassignedPoints)
            if(it.unassignedPoints == 0){
                setButtonsClickable(false)
            }
        }
    }

    private fun setupOnButtonEnduranceClickListener() {
        binding.btnAddEnduranceCharacteristic.setOnClickListener {
            viewModel.increaseEndurance()
        }
    }

    private fun setupOnButtonStrengthClickListener() {
        binding.btnAddStrengthCharacteristic.setOnClickListener {
            viewModel.increaseStrength()
        }
    }

    private fun setupUserData() {
        viewModel.userData.observe(this){
            binding.tvUserLevel.text = getString(R.string.user_level_text, it.level)
            binding.tvUserName.text = it.username
            binding.ivUserProfileImage.setImageResource(userProfileImages.getResourceId(
                it.profileImageId,
                -1
            ))
        }
    }

    private fun setupCanPressButtons() {
        viewModel.canPressButtons.observe(this){
            if(it){
                setButtonsClickable(true)
            }
            else{
                setButtonsClickable(false)
            }
        }
    }

    private fun setButtonsClickable(isClickable: Boolean) {
        binding.btnAddStrengthCharacteristic.isClickable = isClickable
        binding.btnAddEnduranceCharacteristic.isClickable = isClickable
    }

    override fun onDestroy() {
        super.onDestroy()
        userProfileImages.recycle()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, CharacteristicsActivity::class.java)
        }
    }
}