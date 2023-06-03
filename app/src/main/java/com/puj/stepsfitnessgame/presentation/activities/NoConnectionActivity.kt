package com.puj.stepsfitnessgame.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.stepsfitnessgame.databinding.ActivityNoConnectionBinding

class NoConnectionActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityNoConnectionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, NoConnectionActivity::class.java)
        }
    }
}