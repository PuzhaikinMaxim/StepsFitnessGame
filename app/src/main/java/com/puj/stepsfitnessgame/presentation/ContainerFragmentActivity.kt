package com.puj.stepsfitnessgame.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.puj.stepsfitnessgame.databinding.ActivityMenuContainerBinding

class ContainerFragmentActivity: Activity() {

    val binding by lazy {
        ActivityMenuContainerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, ContainerFragmentActivity::class.java)
        }
    }
}