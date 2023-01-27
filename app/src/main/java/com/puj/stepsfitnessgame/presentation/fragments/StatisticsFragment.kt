package com.puj.stepsfitnessgame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.tabs.TabLayout
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentStatisticsBinding
import com.puj.stepsfitnessgame.databinding.ItemDayOfWeekBinding
import com.puj.stepsfitnessgame.domain.models.statistics.StepData
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.StatisticsViewModel

class StatisticsFragment: Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding
        get() = _binding ?: throw RuntimeException("${_binding.toString()} is not set")

    private lateinit var viewModel: StatisticsViewModel

    private lateinit var mainMenuContainer: MainMenuContainer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(null)
        )[StatisticsViewModel::class.java]

        val activity = requireActivity()
        if(activity is MainMenuContainer){
            mainMenuContainer = activity
        }

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnTabsItemClickListener()
        setupTodayStatistics()
        setupLastWeekStatistics()
        setupGraph()
        setupChangeGoalButton()
    }

    private fun setupTodayStatistics() {
        viewModel.todayStatistics.observe(requireActivity()){
            binding.tvTodayGoalStatus.text = getString(
                R.string.statistics_today_step_count_goal,
                it.stepAmount,
                it.goal
            )
        }
    }

    private fun setupLastWeekStatistics() {
        viewModel.weekStatistics.observe(requireActivity()){
            val container = binding.llWeekStatisticsContainer
            for (elem in it){
                val dayItem = ItemDayOfWeekBinding.inflate(layoutInflater)
                dayItem.tvDayOfWeek.text = elem.dayOfWeek
                dayItem.pbDayProgress.progress = elem.percentOfGoal
                if(elem.percentOfGoal >= 100){
                    dayItem.ivPercentOfGoal.visibility = View.VISIBLE
                }
                container.addView(dayItem.root)
            }
        }
    }

    private fun setupOnTabsItemClickListener() {
        with(binding){
            val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    println("tab selected")
                    if (tab != null) {
                        when(tab.position){
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
            val chart = binding.hbrChart
            val entries = ArrayList<BarEntry>()

            var maxValue = 0
            val labels = ArrayList<String>()
            for((i, elem) in it.withIndex()){
                entries.add(BarEntry(i.toFloat(), elem.stepAmount.toFloat(), elem))

                if(elem.inUiRepresentation == ""){
                    labels.add(elem.formattedDate)
                }
                else {
                    labels.add(elem.inUiRepresentation)
                }

                println(elem)
                if(elem.stepAmount > maxValue){
                    maxValue = elem.stepAmount
                }
            }
            setupSelectedItemStatistics(chart)
            binding.hbrChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            val dataSet = BarDataSet(entries, "")
            dataSet.setDrawValues(false)
            val barData = BarData(dataSet)
            binding.hbrChart.data = barData
            setupChartStyles(chart, maxValue.toFloat())
            binding.hbrChart.invalidate()
        }
    }

    private fun setupChangeGoalButton() {
        binding.tvChangeGoal.setOnClickListener {
            mainMenuContainer.startNewScreen(MainMenuContainer.GOAL_SELECTION_FRAGMENT_CODE)
        }
    }

    private fun setupSelectedItemStatistics(hbrChart: BarChart) {
        hbrChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e != null) {
                    setSelectedDateStatistics(e)
                }
            }

            override fun onNothingSelected() {

            }

        })
    }

    private fun setSelectedDateStatistics(entry: Entry) {
        val data = entry.data as StepData

        val amountOfStepsText: String?

        val amountOfKilometersText: String?

        val averageAmountOfStepsText: String?

        when(getSelectedTab()){
            0 -> {
                amountOfStepsText = getString(R.string.statistics_step_count_day, data.stepAmount)
                println(data.metersAmount)
                amountOfKilometersText = getString(R.string.statistics_kilometers_count_day, data.kilometersPassed)
                averageAmountOfStepsText = ""
            }
            1 -> {
                amountOfStepsText = getString(R.string.statistics_step_count_week, data.stepAmount)
                amountOfKilometersText = getString(R.string.statistics_kilometers_count_week, data.kilometersPassed)
                averageAmountOfStepsText = getString(R.string.statistics_average_steps, data.averageStepAmount)
            }
            2 -> {
                amountOfStepsText = getString(R.string.statistics_step_count_month, data.stepAmount)
                amountOfKilometersText = getString(R.string.statistics_kilometers_count_month, data.kilometersPassed)
                averageAmountOfStepsText = getString(R.string.statistics_average_steps, data.averageStepAmount)
            }
            else -> {
                throw RuntimeException("Selected tab is not specified")
            }
        }

        binding.tvAmountOfStepsOnDate.text = amountOfStepsText
        binding.tvAmountOfKilometersOnDate.text = amountOfKilometersText
        binding.tvAverageAmountOfStepsOnDate.text = averageAmountOfStepsText
    }

    private fun getSelectedTab(): Int{
        return binding.tlScale.selectedTabPosition
    }

    private fun setupChartStyles(
        chart: BarChart,
        maximumYAxisValue: Float
    ) {
        chart.setVisibleXRangeMaximum(7f)
        chart.legend.isEnabled = false
        chart.description.text = ""
        chart.isDragYEnabled = false
        chart.axisLeft.isEnabled = false
        with(chart.axisRight){
            setDrawAxisLine(false)
            setLabelCount(3, true)
            axisMinimum = 0f
            axisMaximum = maximumYAxisValue
        }
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.setDrawAxisLine(false)
        chart.setScaleEnabled(false)
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