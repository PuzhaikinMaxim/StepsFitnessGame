package com.puj.stepsfitnessgame.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.stepsfitnessgame.databinding.ActivityUserProfileBinding

class UserProfileActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityUserProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, UserProfileActivity::class.java)
        }
    }
}