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
import com.puj.stepsfitnessgame.databinding.FragmentDuelStatisticsBinding
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.DuelStatisticsViewModel

class DuelStatisticsFragment : Fragment() {

    private var _binding: FragmentDuelStatisticsBinding? = null
    private val binding: FragmentDuelStatisticsBinding
        get() = _binding ?: throw RuntimeException("FragmentDuelStatisticsBinding is null")

    private lateinit var viewModel: DuelStatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDuelStatisticsBinding.inflate(inflater,container,false)

        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()
        viewModel = ViewModelProvider(
            this, ViewModelFactory(sharedPref)
        )[DuelStatisticsViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDuelStatistics()
        setupStartDuelButton()
    }

    private fun setupDuelStatistics() {
        viewModel.duelStatistics.observe(requireActivity()){
            with(binding){
                tvAmountOfDuelsLost.text = getString(
                    R.string.amount_of_duels_lost_text,
                    it.amountOfDuelsLost
                )
                tvAmountOfDuelsWon.text = getString(
                    R.string.amount_of_duels_won,
                    it.amountOfDuelsWon
                )
                val rankText = requireActivity().resources.getStringArray(R.array.ranks)
                tvUserRank.text = getString(
                    R.string.user_rank_text,
                    rankText[it.rank]
                )
                setRankImage(it.rank)
            }
        }
    }

    private fun setRankImage(rank: Int) {
         val imageResource = when(rank){
             0 -> {
                 R.drawable.ic_no_rank
             }
             1 -> {
                 R.drawable.ic_bronze
             }
             2 -> {
                 R.drawable.ic_silver
             }
             3 -> {
                 R.drawable.ic_gold
             }
             else -> {
                 R.drawable.ic_no_rank
             }
         }

        binding.ivRankImage.setImageResource(imageResource)
    }

    private fun setupStartDuelButton() {
        binding.btnStartDuel.setOnClickListener {
            findNavController().navigate(
                R.id.action_duelStatisticsFragment_to_duelSearchFragment
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        removeAllObservers()
    }

    private fun removeAllObservers() {
        viewModel.duelStatistics.removeObservers(requireActivity())
    }

    companion object {

        fun newFragment(): DuelStatisticsFragment {
            return DuelStatisticsFragment()
        }
    }
}