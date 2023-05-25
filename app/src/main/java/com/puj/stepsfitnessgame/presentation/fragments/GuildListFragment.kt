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
import com.puj.stepsfitnessgame.databinding.FragmentGuildsListBinding
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.guild.GuildListAdapter
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
    ): View {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGuildList()
        setupCurrentGuild()
        setupCreateGuildButton()
    }

    private fun setupGuildList() {
        val adapter = GuildListAdapter()
        viewModel.guildList.observe(requireActivity()){
            adapter.guildList = it
        }
        adapter.onJoinGuild = {
            if(it.isEnterRequested){
                viewModel.cancelEnterUseCase(it.guildId)
            }
            else{
                viewModel.requestEnterUseCase(it.guildId)
            }
        }
        binding.rvGuildList.adapter = adapter
    }

    private fun setupCurrentGuild() {
        binding.btnGoToGuild.visibility = View.GONE
        viewModel.guildData.observe(requireActivity()){
            binding.btnCreateGuild.isEnabled = false
            binding.btnGoToGuild.visibility = View.VISIBLE
            binding.tvCurrentGuild.text = getString(R.string.current_guild_text, it.guildName)
            binding.btnGoToGuild.setOnClickListener {
                findNavController().navigate(
                    R.id.action_guildListFragment_to_guildFragment
                )
            }
        }
    }

    private fun setupCreateGuildButton() {
        binding.btnCreateGuild.visibility = View.GONE
        viewModel.guildList.observe(requireActivity()){
            binding.btnCreateGuild.visibility = View.VISIBLE
            binding.btnCreateGuild.setOnClickListener {
                findNavController().navigate(
                    GuildListFragmentDirections.actionGuildListFragmentToGuildEditorFragment(
                        GuildEditorFragment.TYPE_CREATE
                    )
                )
                //mainMenuContainer.startNewScreen(MainMenuContainer.GUILD_CREATION_FRAGMENT_CODE)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newFragment(): GuildListFragment {
            return GuildListFragment()
        }
    }
}