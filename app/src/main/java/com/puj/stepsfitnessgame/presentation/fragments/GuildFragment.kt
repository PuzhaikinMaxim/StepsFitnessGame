package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentGuildBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.guildparticipant.GuildParticipantAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildViewModel

class GuildFragment: Fragment() {

    private var _binding: FragmentGuildBinding? = null
    private val binding: FragmentGuildBinding
        get() = _binding ?: throw RuntimeException("Fragment guild binding is null")

    private lateinit var viewModel: GuildViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuildBinding.inflate(inflater, container, false)
        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[GuildViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGuildStatistics()
        setupGuildData()
        setupIsGuildOwner()
        setupHasReward()
    }

    private fun setupIsGuildOwner() {
        viewModel.isOwner.observe(requireActivity()){
            if(it){
                binding.btnSettings.visibility = View.VISIBLE
                binding.btnEnterRequests.visibility = View.VISIBLE
            }
            setupParticipantList(it)
        }
    }

    private fun setupHasReward() {
        viewModel.hasReward.observe(requireActivity()){
            if(it){
                binding.btnClaimReward.visibility = View.VISIBLE
                binding.btnClaimReward.setOnClickListener {
                    viewModel.claimReward()
                }
            }
        }
    }

    private fun setupParticipantList(isGuildOwner: Boolean) {
        val adapter = GuildParticipantAdapter(isGuildOwner)
        viewModel.guildParticipants.observe(requireActivity()){
            adapter.guildParticipantList = it
        }
        adapter.onExpelParticipant = {
            viewModel.expelGuildParticipant(it)
        }
        binding.rvParticipantList.adapter = adapter
    }

    private fun setupGuildData() {
        viewModel.guildData.observe(requireActivity()){
            with(binding){
                tvGuildName.text = it.guildName
                tvGuildRank.text = getString(R.string.guild_rank, it.guildRank)
                ivGuildLogo.setImageResource(it.guildLogoId)
            }
        }
    }

    private fun setupGuildStatistics() {
        viewModel.guildStatistics.observe(requireActivity()){
            with(binding){
                tvAmountOfCompletedChallenges.text = getString(
                    R.string.amount_of_completed_challenges_text,
                    it.amountOfCompletedChallenges
                )
                tvAmountOfParticipants.text = getString(
                    R.string.amount_of_participants_text,
                    it.amountOfParticipants
                )
                tvCollectiveLevel.text = getString(
                    R.string.collective_level_text,
                    it.participantsCollectiveLevel
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newFragment(): GuildFragment {
            return GuildFragment()
        }
    }
}