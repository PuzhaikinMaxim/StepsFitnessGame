package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.databinding.FragmentChallengeListBinding
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.ChallengeListViewModel

class ChallengeListFragment: Fragment() {

    private var _binding: FragmentChallengeListBinding? = null
    private val binding: FragmentChallengeListBinding
        get() = _binding ?: throw RuntimeException("${_binding.toString()} is not set")

    private lateinit var viewModel: ChallengeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengeListBinding.inflate(inflater, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: throw RuntimeException()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[ChallengeListViewModel::class.java]
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}