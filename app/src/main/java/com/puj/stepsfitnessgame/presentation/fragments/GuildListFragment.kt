package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.databinding.FragmentGuildsListBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildCreationViewModel
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildListViewModel

class GuildListFragment: Fragment() {

    private var _binding: FragmentGuildsListBinding? = null
    private val binding: FragmentGuildsListBinding
        get() = _binding ?: throw RuntimeException("Fragment guild list binding is null")

    private lateinit var viewModel: GuildListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuildsListBinding.inflate(inflater, container, false)
        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[GuildListViewModel::class.java]
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}