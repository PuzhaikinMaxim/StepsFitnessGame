package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentGuildBinding
import com.puj.stepsfitnessgame.databinding.LayoutChallengeBinding
import com.puj.stepsfitnessgame.databinding.LayoutNoChallengeBinding
import com.puj.stepsfitnessgame.databinding.LayoutStartChallengeBinding
import com.puj.stepsfitnessgame.domain.models.guild.CurrentGuildChallenge
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.guildparticipant.GuildParticipantAdapter
import com.puj.stepsfitnessgame.presentation.adapters.items.ItemListAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildViewModel

class GuildFragment: Fragment() {

    private var _binding: FragmentGuildBinding? = null
    private val binding: FragmentGuildBinding
        get() = _binding ?: throw RuntimeException("Fragment guild binding is null")

    private lateinit var viewModel: GuildViewModel

    private lateinit var mainMenuContainer: MainMenuContainer

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
        val activity = requireActivity()
        if(activity is MainMenuContainer){
            mainMenuContainer = activity
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGuildStatistics()
        setupGuildData()
        setupIsGuildOwner()
        setupHasReward()
        setupRewardModal()
    }

    private fun setupCurrentChallenge(isGuildOwner: Boolean) {
        viewModel.currentChallenge.observe(requireActivity()){
            if(it != null){
                setChallenge(it)
                return@observe
            }
            if(isGuildOwner){
                setStartChallenge()
            }
            else{
                setNoChallenge()
            }
        }
    }

    private fun setChallenge(currentGuildChallenge: CurrentGuildChallenge) {
        val challengeBinding = LayoutChallengeBinding.inflate(LayoutInflater.from(
            requireActivity()
        ), binding.clChallengeContainer, false)
        val clChallengeContainer = binding.clChallengeContainer
        clChallengeContainer.addView(challengeBinding.root)

        challengeBinding.pbChallengeProgress.progress = currentGuildChallenge.progress
        challengeBinding.tvGoal.text = getString(
            R.string.goal,
            currentGuildChallenge.goal
        )
        challengeBinding.tvPointsGained.text = getString(
            R.string.points_gained,
            currentGuildChallenge.pointsGained
        )
        challengeBinding.tvTimeTillEnd.text = getString(
            R.string.time_till_end,
            currentGuildChallenge.timeTillEnd
        )

        setupChallengeLayoutParams(challengeBinding.root)
    }

    private fun setNoChallenge() {
        val noChallengeBinding = LayoutNoChallengeBinding.inflate(
            LayoutInflater.from(requireActivity()),
            binding.clChallengeContainer,
            false
        )
        binding.clChallengeContainer.removeAllViews()
        binding.clChallengeContainer.addView(noChallengeBinding.root)
        setupChallengeLayoutParams(noChallengeBinding.root)
    }

    private fun setStartChallenge() {
        val startChallengeBinding = LayoutStartChallengeBinding.inflate(
            LayoutInflater.from(requireActivity()),
            binding.clChallengeContainer,
            false
        )
        binding.clChallengeContainer.removeAllViews()
        binding.clChallengeContainer.addView(startChallengeBinding.root)
        startChallengeBinding.btnStartChallenge.setOnClickListener {
            mainMenuContainer.startNewScreen(MainMenuContainer.CHOOSE_GUILD_CHALLENGE_FRAGMENT_CODE)
        }
        setupChallengeLayoutParams(startChallengeBinding.root)
    }

    private fun setupChallengeLayoutParams(view: View) {
        val clChallengeContainer = binding.clChallengeContainer
        view.updateLayoutParams<ConstraintLayout.LayoutParams> {
            startToStart = clChallengeContainer.id
            endToEnd = clChallengeContainer.id
            topToTop = clChallengeContainer.id
            bottomToBottom = clChallengeContainer.id
        }
    }

    private fun setupIsGuildOwner() {
        viewModel.isOwner.observe(requireActivity()){
            if(it){
                binding.btnSettings.visibility = View.VISIBLE
                binding.btnEnterRequests.visibility = View.VISIBLE
                binding.ivSettings.visibility = View.VISIBLE
                binding.ivEnterRequests.visibility = View.VISIBLE
            }
            setupParticipantList(it)
            setupCurrentChallenge(it)
        }
    }

    private fun setupHasReward() {
        viewModel.hasReward.observe(requireActivity()){
            if(it){
                binding.btnClaimReward.visibility = View.VISIBLE
                binding.ivClaimReward.visibility = View.VISIBLE
                binding.btnClaimReward.setOnClickListener {
                    viewModel.claimReward()
                }
            }
            else{
                binding.btnClaimReward.visibility = View.GONE
            }
        }
    }

    private fun setupRewardModal() {
        setupReward()
        viewModel.shouldOpenRewardModal.observe(requireActivity()){
            if(it){
                binding.lReward.root.visibility = View.VISIBLE
            }
            else{
                binding.lReward.root.visibility = View.GONE
            }
        }
    }

    private fun setupReward() {
        val adapter = ItemListAdapter()
        viewModel.challengeReward.observe(requireActivity()){
            with(binding.lReward){
                tvAmountOfXpGained.text = getString(R.string.amount_of_xp_gained, it.amountOfXp)
                adapter.itemsList = it.itemsList
                btnConfirm.setOnClickListener {
                    viewModel.closeRewardModal()
                }
            }
        }
        binding.lReward.rvGainedItems.adapter = adapter
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