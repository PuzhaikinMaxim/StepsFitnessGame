package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.databinding.FragmentSelectLevelBinding
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.levellist.LevelAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.SelectLevelViewModel

class SelectLevelFragment: Fragment() {

    private var _binding: FragmentSelectLevelBinding? = null
    private val binding: FragmentSelectLevelBinding
        get() = _binding ?: throw RuntimeException("Choose level fragment not set")

    private lateinit var viewModel: SelectLevelViewModel

    private lateinit var mainMenuContainer: MainMenuContainer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectLevelBinding.inflate(
            inflater,
            container,
            false
        )

        val sharedPreferences = activity?.getPreferences(
            Context.MODE_PRIVATE
        ) ?: throw RuntimeException()

        viewModel = ViewModelProvider(this,
            ViewModelFactory(sharedPreferences)
        )[SelectLevelViewModel::class.java]

        val activity = requireActivity()
        if(activity is MainMenuContainer){
            mainMenuContainer = activity
        }

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

        adapter.onLevelClickListener = {
            viewModel.selectLevel(it.dungeonLevel)
            mainMenuContainer.startNewScreen(MainMenuContainer.BACK_TO_CHALLENGE_LIST_CODE)
        }

        binding.rvLevelList.adapter = adapter
    }

    companion object {

        fun newFragment(): SelectLevelFragment {
            return SelectLevelFragment()
        }
    }
}