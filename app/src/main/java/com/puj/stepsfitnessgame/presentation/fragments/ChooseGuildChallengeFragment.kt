package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentChooseGuildChallengeBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.guildchallenges.GuildChallengesAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.ChooseGuildChallengeViewModel

class ChooseGuildChallengeFragment: Fragment() {

    private var _binding: FragmentChooseGuildChallengeBinding? = null
    private val binding: FragmentChooseGuildChallengeBinding
        get() = _binding ?: throw RuntimeException("Fragment choose guild challenge binding is null")

    private lateinit var viewModel: ChooseGuildChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseGuildChallengeBinding.inflate(inflater, container, false)
        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[ChooseGuildChallengeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGuildChallengeList()
        setupShouldCloseScreen()
        setupOnBackPressed()
    }

    private fun setupGuildChallengeList() {
        val adapter = GuildChallengesAdapter()
        viewModel.guildChallenges.observe(requireActivity()){
            adapter.challengesList = it
        }
        adapter.onStartButtonClick = {
            viewModel.selectGuildChallenge(it)
        }
        binding.rvChooseChallenge.adapter = adapter
    }

    private fun setupShouldCloseScreen() {
        viewModel.shouldCloseScreen.observe(requireActivity()){
            findNavController().navigate(
                R.id.action_chooseGuildChallengeFragment_to_guildFragment
            )
        }
    }

    private fun setupOnBackPressed() {
        binding.ivClose.setOnClickListener {
            findNavController().navigate(
                R.id.action_chooseGuildChallengeFragment_to_guildFragment
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        removeAllObservers()
    }

    private fun removeAllObservers() {
        if(!this::viewModel.isInitialized) return
        viewModel.guildChallenges.removeObservers(requireActivity())
        viewModel.shouldCloseScreen.removeObservers(requireActivity())
    }

    companion object {

        fun newFragment(): ChooseGuildChallengeFragment {
            return ChooseGuildChallengeFragment()
        }
    }
}