package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentDuelSearchBinding
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.DuelSearchViewModel

class DuelSearchFragment : Fragment() {

    private var _binding: FragmentDuelSearchBinding? = null
    private val binding: FragmentDuelSearchBinding
        get() = _binding ?: throw RuntimeException("FragmentDuelSearchBinding is null")

    private lateinit var viewModel: DuelSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDuelSearchBinding.inflate(inflater,container,false)

        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()
        viewModel = ViewModelProvider(
            this, ViewModelFactory(sharedPref)
        )[DuelSearchViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnCancelSearchButtonListener()
        viewModel.startTimer()
        setupTimer()
        setupOnOpponentFound()
    }

    private fun setOnCancelSearchButtonListener() {
        binding.btnCancelSearch.setOnClickListener {
            viewModel.stopSearch()
            requireActivity().onBackPressed()
        }
    }

    private fun setupTimer() {
        viewModel.timer.observe(requireActivity()){
            binding.tvSecondsPassed.text = getString(R.string.seconds_passed_text, it)
        }
    }

    private fun setupOnOpponentFound() {
        viewModel.isOpponentFound.observe(requireActivity()){
            if(it){
                findNavController().navigate(
                    R.id.action_duelSearchFragment_to_duelFieldFragment
                )
            }
        }
    }

    override fun onStop() {
        viewModel.stopSearch()
        super.onStop()
    }

    override fun onPause() {
        viewModel.stopSearch()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newFragment(): DuelSearchFragment {
            return DuelSearchFragment()
        }
    }
}