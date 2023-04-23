package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.databinding.FragmentGuildEnterRequestsBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildEnterRequestsViewModel

class GuildEnterRequestsFragment: Fragment() {

    private var _binding: FragmentGuildEnterRequestsBinding? = null
    private val binding: FragmentGuildEnterRequestsBinding
        get() = _binding ?: throw RuntimeException("Fragment guild enter request binding is null")

    private lateinit var viewModel: GuildEnterRequestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuildEnterRequestsBinding.inflate(inflater, container, false)
        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[GuildEnterRequestsViewModel::class.java]
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}