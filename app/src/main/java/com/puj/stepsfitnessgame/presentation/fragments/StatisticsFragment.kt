package com.puj.stepsfitnessgame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.tabs.TabLayout
import com.puj.stepsfitnessgame.databinding.FragmentStatisticsBinding
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.StatisticsViewModel

class StatisticsFragment: Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding
        get() = _binding ?: throw RuntimeException("${_binding.toString()} is not set")

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(null)
        )[StatisticsViewModel::class.java]

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnTabsItemClickListener()
        setupGraph()
    }

    private fun setupOnTabsItemClickListener() {
        with(binding){
            val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        when(tab.id){
                            0 -> {
                                viewModel.setLastThirtyDaysStepData()
                            }
                            1 -> {
                                viewModel.setLastTwelveWeeksStepData()
                            }
                            2 -> {
                                viewModel.setLastTwelveMonthsStepData()
                            }
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            }
            tlScale.addOnTabSelectedListener(onTabSelectedListener)
        }
    }

    private fun setupGraph() {
        viewModel.stepData.observe(requireActivity()){
            val entries = ArrayList<BarEntry>()
            for((i, elem) in it.withIndex()){
                entries.add(BarEntry(i.toFloat(), elem.stepAmount.toFloat()))
                println(elem)
            }
            val dataSet = BarDataSet(entries, "Test")
            val barData = BarData(dataSet)
            binding.hbrChart.data = barData
            binding.hbrChart.invalidate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newFragment(): Fragment {
            return StatisticsFragment()
        }
    }
}