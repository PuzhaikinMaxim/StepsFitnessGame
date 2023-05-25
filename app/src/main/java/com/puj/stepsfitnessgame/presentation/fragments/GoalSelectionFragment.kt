package com.puj.stepsfitnessgame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentGoalSelectionBinding
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.goal.GoalListAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.GoalSelectionViewModel

class GoalSelectionFragment: Fragment() {

    private var _binding: FragmentGoalSelectionBinding? = null
    private val binding: FragmentGoalSelectionBinding
        get() = _binding ?: throw RuntimeException("${_binding.toString()} is not set")

    private lateinit var viewModel: GoalSelectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalSelectionBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(null)
        )[GoalSelectionViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupCloseScreen()
    }

    private fun setupCloseScreen() {
        viewModel.shouldCloseScreen.observe(requireActivity()){
            findNavController().navigate(
                R.id.action_goalSelectionFragment_to_statisticsFragment
            )
        }
    }

    private fun setupRecyclerView() {
        val adapter = GoalListAdapter()

        viewModel.goalList.observe(requireActivity()){
            adapter.goalList = it
        }

        adapter.onGoalPressedCallback = {
            viewModel.setGoal(it)
        }

        binding.rvGoalItemsList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newFragment(): GoalSelectionFragment {
            return GoalSelectionFragment()
        }
    }
}