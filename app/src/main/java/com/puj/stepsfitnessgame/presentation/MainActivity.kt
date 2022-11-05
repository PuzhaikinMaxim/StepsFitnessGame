package com.puj.stepsfitnessgame.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fc_main, AuthFragment.newFragment())
            .addToBackStack(null)
            .commit()
    }
}