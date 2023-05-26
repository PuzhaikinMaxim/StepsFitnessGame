package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentGuildEditorBinding
import com.puj.stepsfitnessgame.domain.usecases.guild.EditGuildUseCase
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.guildlogo.GuildLogoAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildCreationViewModel
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildEditionViewModel
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildEditorViewModel

class GuildEditorFragment: Fragment() {

    private var _binding: FragmentGuildEditorBinding? = null
    private val binding: FragmentGuildEditorBinding
        get() = _binding ?: throw RuntimeException("Fragment guild creation binding is null")

    private val args by navArgs<GuildEditorFragmentArgs>()

    private lateinit var viewModel: GuildEditorViewModel

    private lateinit var guildLogoIds: TypedArray

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuildEditorBinding.inflate(inflater, container, false)
        guildLogoIds = resources.obtainTypedArray(R.array.guild_logos)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
    }

    private fun parseParams() {
        when(args.editorType){
            TYPE_EDIT -> {
                viewModel = ViewModelProvider(
                    this,
                    ViewModelFactory(getSharedPreferences())
                )[GuildEditionViewModel::class.java]
                viewModel.createLogoList(guildLogoIds)
                setEditGuild()
            }
            TYPE_CREATE -> {
                viewModel = ViewModelProvider(
                    this,
                    ViewModelFactory(getSharedPreferences())
                )[GuildCreationViewModel::class.java]
                setupOnCreateButtonListener()
            }
        }
        setupLogoList()
        setupShouldCloseScreenWindow()
    }

    private fun setEditGuild() {
        val editGuildViewModel = viewModel as GuildEditionViewModel
        editGuildViewModel.guildEditionInfo.observe(requireActivity()){
            viewModel.selectGuildLogo(it.guildLogoId)
            binding.etGuildName.setText(it.guildName)
        }
        binding.btnEditGuild.visibility = View.VISIBLE
        binding.btnCreateGuild.visibility = View.GONE
        binding.btnEditGuild.setOnClickListener {
            val guildName = binding.etGuildName.text.toString()
            editGuildViewModel.editGuild(guildName)
        }
    }

    private fun getSharedPreferences(): SharedPreferences {
        return activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()
    }

    private fun setupLogoList() {
        val adapter = GuildLogoAdapter(requireActivity())
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
            (viewModel as GuildCreationViewModel).creteGuild(guildName)
        }
    }

    private fun setupShouldCloseScreenWindow() {
        viewModel.shouldCloseScreen.observe(requireActivity()){
            findNavController().navigate(
                GuildEditorFragmentDirections.actionGuildEditorFragmentToGuildFragment().apply {
                    isDataChanged = args.editorType == TYPE_EDIT
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val EDITOR_TYPE_KEY = "editor_type_key"

        const val TYPE_CREATE = "type_create"

        const val TYPE_EDIT = "type_edit"

        fun newFragment(): GuildEditorFragment {
            return GuildEditorFragment()
        }

        fun newGuildCreationFragment(): GuildEditorFragment {
            val fragment = GuildEditorFragment()
            val bundle = Bundle()
            bundle.putString(EDITOR_TYPE_KEY, TYPE_CREATE)
            fragment.arguments = bundle
            return fragment
        }

        fun newGuildEditionFragment(): GuildEditorFragment {
            val fragment = GuildEditorFragment()
            val bundle = Bundle()
            bundle.putString(EDITOR_TYPE_KEY, TYPE_EDIT)
            fragment.arguments = bundle
            return fragment
        }
    }
}