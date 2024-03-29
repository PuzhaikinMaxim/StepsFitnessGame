package com.puj.stepsfitnessgame.presentation.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentChallengeListBinding
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.challengelist.ChallengeListAdapter
import com.puj.stepsfitnessgame.presentation.adapters.challengelist.ChallengeListItemAnimator
import com.puj.stepsfitnessgame.presentation.adapters.items.ItemListAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.ChallengeListViewModel

class ChallengeListFragment: Fragment() {

    private var _binding: FragmentChallengeListBinding? = null
    private val binding: FragmentChallengeListBinding
        get() = _binding ?: throw RuntimeException("${_binding.toString()} is not set")

    private lateinit var itemImgIds: TypedArray

    private lateinit var viewModel: ChallengeListViewModel

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(requireActivity())
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when(p1?.action) {
                "step_count_updated" -> {
                    viewModel.updateData()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengeListBinding.inflate(inflater, container, false)
        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()
        viewModel = ViewModelProvider(
            this, ViewModelFactory(sharedPref)
        )[ChallengeListViewModel::class.java]
        itemImgIds = resources.obtainTypedArray(R.array.item_imgs)
        //Toast.makeText(requireActivity(), "Test", Toast.LENGTH_LONG).show()
        setupTodayStatistics()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChallengeList()
        setupChooseLevelButton()
        setShowStatisticsButton()
        setupChallengeStatistics()
        setupItemsModal()
        setupIntentFilter()
    }

    private fun setupIntentFilter() {
        val intentFilter = IntentFilter().apply {
            addAction("step_count_updated")
        }
        localBroadcastManager.registerReceiver(receiver, intentFilter)
    }

    private fun setupTodayStatistics() {
        viewModel.todayStatistics.observe(requireActivity()){
            binding.tvAmountOfStepsToday.text = getString(
                R.string.statistics_amount_of_steps_passed,
                it.stepAmount,
                it.kilometersPassed
            )
            binding.tvPercentOfGoalCompleted.text = getString(
                R.string.statistics_percent_of_completion,
                it.percentOfGoal
            )
            binding.pbDailyStepCountProgress.progress = it.percentOfGoal
        }
    }

    private fun setupChallengeList() {
        val adapter = ChallengeListAdapter()
        adapter.onItemIsShownListener = {
            viewModel.changeChallengeDetailsVisibility(it)
        }

        adapter.onItemChallengeStartListener = {
            viewModel.startChallenge(it)
        }

        adapter.onItemChallengeCancelListener = {
            viewModel.cancelActiveChallenge()
        }

        adapter.onItemChallengeEndListener = {
            viewModel.endActiveChallenge()
        }

        binding.rvChallengesList.recycledViewPool.setMaxRecycledViews(
            ChallengeListAdapter.VIEW_TYPE_STARTED,
            1
        )

        binding.rvChallengesList.recycledViewPool.setMaxRecycledViews(
            ChallengeListAdapter.VIEW_TYPE_STARTED,
            ChallengeListAdapter.MAX_POOL_SIZE
        )

        viewModel.challengeList.observe(requireActivity()){
            adapter.challengeList = it
        }

        val challengeListItemAnimator = ChallengeListItemAnimator()

        binding.rvChallengesList.itemAnimator = challengeListItemAnimator

        binding.rvChallengesList.adapter = adapter
    }

    private fun setShowStatisticsButton() {
        binding.btnShowStatisticsButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeListFragment_to_statisticsFragment
            )
        }
    }

    private fun setupChallengeStatistics() {
        viewModel.challengeStatistics.observe(requireActivity()){
            binding.tvAmountOfCompletedChallenges.text = getString(
                R.string.statistics_amount_of_completed_challenges,
                it.amountOfCompletedChallenges,
                it.amountOfChallenges
            )
        }
    }

    private fun setupChooseLevelButton() {
        binding.btnChooseLevel.setOnClickListener {
            findNavController().navigate(
                R.id.action_challengeListFragment_to_selectLevelFragment
            )
        }
    }

    private fun setupItemsModal() {
        val modal = binding.lModal

        viewModel.shouldShowRewardModal.observe(requireActivity()){
            if(it){
                modal.clLayoutRewardContainer.visibility = View.VISIBLE
            }
            else{
                modal.clLayoutRewardContainer.visibility = View.GONE
            }
        }

        val adapter = ItemListAdapter(
            resources.getStringArray(R.array.item_rarities_colors).asList(),
            itemImgIds
        )

        viewModel.completedChallengeReward.observe(requireActivity()){
            if(it != null){
                adapter.itemsList = it.itemsList
                modal.tvAmountOfXpGained.text = getString(
                    R.string.amount_of_xp_gained,
                    it.amountOfXp
                )
            }
        }

        modal.rvGainedItems.adapter = adapter
        modal.tvHeaderMessage.text = getString(R.string.header_message_challenge)
        modal.btnConfirm.setOnClickListener {
            viewModel.closeRewardModal()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
        removeAllObservers()
        itemImgIds.recycle()
    }

    private fun removeAllObservers() {
        if(!this::viewModel.isInitialized) return
        viewModel.challengeList.removeObservers(requireActivity())
        viewModel.completedChallengeReward.removeObservers(requireActivity())
        viewModel.todayStatistics.removeObservers(requireActivity())
        viewModel.shouldShowRewardModal.removeObservers(requireActivity())
        viewModel.challengeStatistics.removeObservers(requireActivity())
    }

    companion object {

        fun newFragment(): ChallengeListFragment {
            return ChallengeListFragment()
        }
    }
}