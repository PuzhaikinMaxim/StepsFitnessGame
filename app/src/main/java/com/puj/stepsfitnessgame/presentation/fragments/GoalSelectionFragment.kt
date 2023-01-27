package com.puj.stepsfitnessgame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puj.stepsfitnessgame.databinding.FragmentGoalSelectionBinding
import com.puj.stepsfitnessgame.presentation.viewmodels.ChallengeListViewModel
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
        return binding.root
    }

    companion object {

        fun newFragment(): GoalSelectionFragment {
            return GoalSelectionFragment()
        }
    }
}