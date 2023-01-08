package com.puj.stepsfitnessgame.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ActivityMenuContainerBinding
import com.puj.stepsfitnessgame.presentation.fragments.ChallengeListFragment

class ContainerFragmentActivity: AppCompatActivity() {

    val binding by lazy {
        ActivityMenuContainerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        openChallengeListFragment()
    }

    private fun openChallengeListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fc_main_fragments_container, ChallengeListFragment.newFragment())
            .addToBackStack(null)
            .commit()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, ContainerFragmentActivity::class.java)
        }
    }
}