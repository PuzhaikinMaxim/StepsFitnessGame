package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentRatingBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.rating.RatingAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.GuildListViewModel
import com.puj.stepsfitnessgame.presentation.viewmodels.RatingViewModel

class RatingFragment: Fragment() {

    private var _binding: FragmentRatingBinding? = null
    private val binding: FragmentRatingBinding
        get() = _binding ?: throw RuntimeException("Fragment rating binding is null")

    private lateinit var viewModel: RatingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(
            inflater,
            container,
            false
        )
        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[RatingViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRatingList()
        setupRatingTypeMenu()
        setupCountdownTimer()
    }

    private fun setupRatingList() {
        val adapter = RatingAdapter(requireActivity())
        viewModel.ratingList.observe(requireActivity()){
            adapter.ratingList = it
        }
        binding.rvPlayerRating.adapter = adapter
    }

    private fun setupCountdownTimer() {
        viewModel.ratingListUpdateCountdown.observe(requireActivity()){
            binding.tvListUpdateCountdownText.text = getString(
                R.string.tv_list_update_countdown_text,
                it
            )
        }
    }

    private fun setupRatingTypeMenu() {
        val onTabSelectedListener = object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null){
                    when(tab.position){
                        0 -> {
                            viewModel.setStepAmountRating()
                        }
                        1 -> {
                            viewModel.setDuelsAmountRating()
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        }
        binding.tlRatingType.addOnTabSelectedListener(onTabSelectedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        removeAllObservers()
    }

    private fun removeAllObservers() {
        if(!this::viewModel.isInitialized) return
        viewModel.ratingList.removeObservers(requireActivity())
        viewModel.ratingListUpdateCountdown.removeObservers(requireActivity())
    }

    companion object {

        fun newFragment(): RatingFragment {
            return RatingFragment()
        }

    }
}