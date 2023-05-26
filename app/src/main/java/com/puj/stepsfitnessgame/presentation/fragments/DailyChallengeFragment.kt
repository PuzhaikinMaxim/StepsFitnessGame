package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentDailyChallengesBinding
import com.puj.stepsfitnessgame.databinding.ItemDailyTaskBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.items.ItemListAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.DailyChallengeViewModel

class DailyChallengeFragment: Fragment() {

    private var _binding: FragmentDailyChallengesBinding? = null
    private val binding: FragmentDailyChallengesBinding
        get() = _binding ?: throw RuntimeException("${_binding.toString()} is not set")

    private lateinit var viewModel: DailyChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyChallengesBinding.inflate(inflater, container, false)
        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[DailyChallengeViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDailyTasksList()
        setupAmountOfDailyTasksCompletedTextView()
        setupClaimRewardButton()
        setupModal()
    }

    private fun setupDailyTasksList() {
        viewModel.dailyTasksList.observe(requireActivity()){
            binding.llDailyTask.removeAllViews()
            println(it)
            for(elem in it){
                val dailyTaskItem = ItemDailyTaskBinding.inflate(
                    layoutInflater,
                    binding.llDailyTask,
                    false
                )
                with(dailyTaskItem){
                    tvTaskDescription.text = getString(
                        R.string.task_description, elem.amountOfSteps
                    )
                    if(elem.isCompleted){
                        ivTaskCompleted.visibility = View.VISIBLE
                        vTaskCrossing.visibility = View.VISIBLE
                    }
                }
                binding.llDailyTask.addView(dailyTaskItem.root)
            }
        }
    }

    private fun setupAmountOfDailyTasksCompletedTextView() {
        viewModel.dailyTasksList.observe(requireActivity()){
            binding.tvDailyTasksCompleted.text = getString(
                R.string.fragment_daily_tasks_amount_of_completed_daily_tasks_text,
                viewModel.amountOfCompletedTasks,
                viewModel.amountOfTasks
            )
        }
    }

    private fun setupClaimRewardButton() {
        binding.btnClaimReward.setOnClickListener {
            viewModel.claimDailyChallengeReward()
        }
    }

    private fun setupModal() {
        viewModel.shouldShowRewardModal.observe(requireActivity()){
            if(it){
                binding.lModal.clLayoutRewardContainer.visibility = View.VISIBLE
            }
            else{
                binding.lModal.clLayoutRewardContainer.visibility = View.GONE
            }
        }

        val adapter = ItemListAdapter()

        viewModel.completedDailyChallengeReward.observe(requireActivity()){
            binding.lModal.tvHeaderMessage.text = getString(R.string.header_message_daily_challenge)
            binding.lModal.tvAmountOfXpGained.text = getString(
                R.string.amount_of_xp_gained,
                it.xp
            )
            adapter.itemsList = it.reward
        }

        binding.lModal.rvGainedItems.adapter = adapter

        binding.lModal.btnConfirm.setOnClickListener {
            viewModel.closeModal()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        removeAllObservers()
    }

    private fun removeAllObservers() {
        viewModel.dailyTasksList.removeObservers(requireActivity())
        viewModel.completedDailyChallengeReward.removeObservers(requireActivity())
        viewModel.shouldShowRewardModal.removeObservers(requireActivity())
    }

    companion object {

        fun newFragment(): DailyChallengeFragment {
            return DailyChallengeFragment()
        }
    }
}