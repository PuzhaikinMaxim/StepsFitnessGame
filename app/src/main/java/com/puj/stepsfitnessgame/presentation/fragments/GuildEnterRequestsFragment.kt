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
import com.puj.stepsfitnessgame.databinding.FragmentGuildEnterRequestsBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.guildenterrequest.GuildEnterRequestAdapter
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
    ): View {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGuildEnterRequestList()
        setupOnBackPressed()
    }

    private fun setupGuildEnterRequestList() {
        val adapter = GuildEnterRequestAdapter()
        viewModel.guildEnterRequestList.observe(requireActivity()){
            adapter.guildEnterRequestList = it
            if(it.isEmpty()){
                binding.tvNoGuildEnterRequests.visibility = View.VISIBLE
            }
        }
        adapter.onAcceptEnter = {
            viewModel.acceptEnter(it)
        }
        adapter.onRefuseEnter = {
            viewModel.refuseEnter(it)
        }
        binding.rvGuildEnterRequest.adapter = adapter
    }

    private fun setupOnBackPressed() {
        binding.ivClose.setOnClickListener {
            findNavController().navigate(
                R.id.action_guildEnterRequestsFragment_to_guildFragment
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
        viewModel.guildEnterRequestList.removeObservers(requireActivity())
    }

    companion object {

        fun newFragment(): GuildEnterRequestsFragment {
            return GuildEnterRequestsFragment()
        }
    }
}