package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.databinding.FragmentGuildEditorBinding
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.guildlogo.GuildLogoAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildCreationViewModel

class GuildEditorFragment: Fragment() {

    private var _binding: FragmentGuildEditorBinding? = null
    private val binding: FragmentGuildEditorBinding
        get() = _binding ?: throw RuntimeException("Fragment guild creation binding is null")

    private lateinit var viewModel: GuildCreationViewModel

    private lateinit var mainMenuContainer: MainMenuContainer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuildEditorBinding.inflate(inflater, container, false)
        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[GuildCreationViewModel::class.java]

        val activity = requireActivity()
        if(activity is MainMenuContainer){
            mainMenuContainer = activity
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLogoList()
        setupOnCreateButtonListener()
        setupShouldCloseScreenWindow()
    }

    private fun setupLogoList() {
        val adapter = GuildLogoAdapter()
        viewModel.guildLogoList.observe(requireActivity()){
            adapter.guildLogoList = it
        }
        adapter.onSelectGuildLogo = {
            viewModel.selectGuildLogo(it)
        }
        binding.rvGuildLogo.adapter = adapter
    }

    private fun setupOnCreateButtonListener() {
        binding.btnCreateGuild.setOnClickListener {
            val guildName = binding.etGuildName.text.toString()
            viewModel.creteGuild(guildName)
        }
    }

    private fun setupShouldCloseScreenWindow() {
        viewModel.shouldCloseScreen.observe(requireActivity()){
            requireActivity().supportFragmentManager.popBackStack()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newFragment(): GuildEditorFragment {
            return GuildEditorFragment()
        }
    }
}