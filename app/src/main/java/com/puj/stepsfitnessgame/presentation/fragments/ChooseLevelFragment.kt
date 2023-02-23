package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.databinding.FragmentChooseLevelBinding
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.levellist.LevelAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.ChooseLevelViewModel

class ChooseLevelFragment: Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("Choose level fragment not set")

    private lateinit var viewModel: ChooseLevelViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(
            inflater,
            container,
            false
        )

        val sharedPreferences = activity?.getPreferences(
            Context.MODE_PRIVATE
        ) ?: throw RuntimeException()

        viewModel = ViewModelProvider(this,
            ViewModelFactory(sharedPreferences)
        )[ChooseLevelViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLevelList()
    }

    private fun setupLevelList() {
        val adapter = LevelAdapter()

        viewModel.levelList.observe(requireActivity()){
            adapter.levelList = it
        }

        binding.rvLevelList.adapter = adapter
    }

    companion object {

        fun newFragment(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }
}