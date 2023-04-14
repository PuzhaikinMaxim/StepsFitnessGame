package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentDuelFieldBinding
import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.DuelFieldViewModel

class DuelFieldFragment : Fragment() {

    private var _binding: FragmentDuelFieldBinding? = null
    private val binding: FragmentDuelFieldBinding
        get() = _binding ?: throw RuntimeException("FragmentDuelFieldBinding is null")

    private lateinit var viewModel: DuelFieldViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDuelFieldBinding.inflate(inflater,container,false)

        val sharedPref =
            activity?.getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()
        viewModel = ViewModelProvider(
            this, ViewModelFactory(sharedPref)
        )[DuelFieldViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGameField()
    }

    private fun setupGameField() {
        viewModel.duelField.observe(requireActivity()){
            setupHealthBars(it)
            binding.tvUserName.text = it.player.name
            binding.tvOpponentName.text = it.opponent.name
        }
    }

    private fun setupHealthBars(duelField: DuelField) {
        with(binding){
            setupHealthBar(
                pbUserHealth,
                tvUserHealth,
                duelField.playerInitialHp,
                duelField.playerHp
            )

            setupHealthBar(
                pbOpponentHealth,
                tvOpponentHealth,
                duelField.opponentInitialHp,
                duelField.opponentHp
            )

            ivUserIcon.setImageResource(R.drawable.ic_user)
            ivOpponentIcon.setImageResource(R.drawable.ic_user)
        }
    }

    private fun setupHealthBar(
        pbHealth: ProgressBar,
        tvHealth: TextView,
        initialHp: Int,
        hp: Int
    ) {
        pbHealth.max = initialHp
        pbHealth.progress = hp
        tvHealth.text = getString(
            R.string.health_text,
            hp,
            initialHp
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newFragment(): DuelFieldFragment {
            return DuelFieldFragment()
        }
    }
}