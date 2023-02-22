package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentDailyTasksBinding
import com.puj.stepsfitnessgame.databinding.ItemDailyTaskBinding
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.DailyTasksViewModel

class DailyTasksFragment: Fragment() {

    private var _binding: FragmentDailyTasksBinding? = null
    private val binding: FragmentDailyTasksBinding
        get() = _binding ?: throw RuntimeException("${_binding.toString()} is not set")

    private lateinit var viewModel: DailyTasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyTasksBinding.inflate(inflater, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: throw RuntimeException()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[DailyTasksViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDailyTasksList()
        setupAmountOfDailyTasksCompletedTextView()
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
                    tvTaskDescription.text = elem.dailyChallengeDescription
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        fun newFragment(): DailyTasksFragment {
            return DailyTasksFragment()
        }
    }
}