package com.puj.stepsfitnessgame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puj.stepsfitnessgame.databinding.FragmentRatingBinding

class RatingFragment: Fragment() {

    private var _binding: FragmentRatingBinding? = null
    private val binding: FragmentRatingBinding
        get() = _binding ?: throw RuntimeException("Fragment rating binding is null")

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
        return binding.root
    }

    companion object {

        fun newFragment(): RatingFragment {
            return RatingFragment()
        }

    }
}