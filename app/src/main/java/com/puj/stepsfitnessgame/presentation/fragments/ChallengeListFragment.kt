package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentChallengeListBinding
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.ChallengeListAdapter
import com.puj.stepsfitnessgame.presentation.adapters.ChallengeListItemAnimator
import com.puj.stepsfitnessgame.presentation.viewmodels.ChallengeListViewModel

class ChallengeListFragment: Fragment() {

    private var _binding: FragmentChallengeListBinding? = null
    private val binding: FragmentChallengeListBinding
        get() = _binding ?: throw RuntimeException("${_binding.toString()} is not set")

    private lateinit var viewModel: ChallengeListViewModel

    private lateinit var mainMenuContainer: MainMenuContainer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengeListBinding.inflate(inflater, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: throw RuntimeException()
        viewModel = ViewModelProvider(
            this, ViewModelFactory(sharedPref)
        )[ChallengeListViewModel::class.java]

        val activity = requireActivity()
        if(activity is MainMenuContainer){
            mainMenuContainer = activity
        }
        setupTodayStatistics()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChallengeList()
        setShowStatisticsButton()
    }

    private fun setupTodayStatistics() {
        viewModel.todayStatistics.observe(requireActivity()){
            println("T: $it")
            binding.tvAmountOfStepsToday.text = getString(
                R.string.statistics_amount_of_steps_passed,
                it.stepAmount,
                it.kilometersPassed
            )
            binding.tvPercentOfGoalCompleted.text = getString(
                R.string.statistics_percent_of_completion,
                it.percentOfGoal
            )

            println("Percent ${it.percentOfGoal}")
            binding.pbDailyStepCountProgress.progress = it.percentOfGoal
        }
    }

    private fun setupChallengeList() {
        val adapter = ChallengeListAdapter()
        adapter.onItemIsShownListener = {
            println("changeable value $it")
            viewModel.changeChallengeDetailsVisibility(it)
        }

        adapter.onItemChallengeStartListener = {
            viewModel.startChallenge(it)
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
            mainMenuContainer.startNewScreen(MainMenuContainer.STATISTICS_FRAGMENT_CODE)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        fun newFragment(): ChallengeListFragment {
            return ChallengeListFragment()
        }
    }
}