package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentDuelFieldBinding
import com.puj.stepsfitnessgame.domain.models.duel.DuelField
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.items.ItemListAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.DuelFieldViewModel

class DuelFieldFragment : Fragment() {

    private var _binding: FragmentDuelFieldBinding? = null
    private val binding: FragmentDuelFieldBinding
        get() = _binding ?: throw RuntimeException("FragmentDuelFieldBinding is null")

    private lateinit var viewModel: DuelFieldViewModel

    private lateinit var playerProfileImages: TypedArray

    private lateinit var itemImgIds: TypedArray

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

        playerProfileImages = resources.obtainTypedArray(R.array.profile_images)
        itemImgIds = resources.obtainTypedArray(R.array.item_imgs)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGameField()
        setupRewardModal()
        setupShouldCloseScreen()
        setupOnGiveUpListener()
        viewModel.startPeriodicalDataUpdate()
    }

    private fun setupGameField() {
        viewModel.duelField.observe(requireActivity()){
            setupHealthBars(it)
            setupGameEndedInfoContainer(it)
            binding.tvUserName.text = it.player.name
            binding.tvUserLevel.text = it.player.level.toString()
            binding.ivUserIcon.setImageResource(playerProfileImages.getResourceId(
                it.player.profileImageId, -1
            ))

            binding.tvOpponentName.text = it.opponent.name
            binding.tvOpponentLevel.text = it.opponent.level.toString()
            binding.ivOpponentIcon.setImageResource(playerProfileImages.getResourceId(
                it.opponent.profileImageId, -1
            ))
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

    private fun setupGameEndedInfoContainer(duelField: DuelField) {
        with(binding){
            if(!duelField.isDuelFinished) return
            clGameEndedInfoContainer.visibility = View.VISIBLE

            if(duelField.isWon){
                tvDuelResult.text = getString(R.string.duel_result_win_text)
            }
            else{
                tvDuelResult.text = getString(R.string.duel_result_lose_text)
            }
            binding.btnClaimReward.setOnClickListener {
                viewModel.claimReward()
            }
            setupDuelResultIcon(duelField)
        }
    }

    private fun setupDuelResultIcon(duelField: DuelField) {
        with(binding){
            if(duelField.isWon){
                ivDuelResult.setImageResource(R.drawable.ic_won)
            }
            else if(!duelField.isDuelCanceled){
                ivDuelResult.setImageResource(R.drawable.ic_lose)
            }
            else {
                ivDuelResult.setImageResource(R.drawable.ic_surrender)
            }
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

    private fun setupRewardModal() {
        binding.lDuelRewardContainer.clLayoutRewardContainer.visibility = View.GONE
        viewModel.finishedDuelReward.observe(requireActivity()){
            binding.lDuelRewardContainer.clLayoutRewardContainer.visibility = View.VISIBLE
            val adapter = ItemListAdapter(
                resources.getStringArray(R.array.item_rarities_colors).asList(),
                itemImgIds
            )
            adapter.itemsList = it.reward
            binding.lDuelRewardContainer.rvGainedItems.adapter = adapter
            binding.lDuelRewardContainer.tvHeaderMessage.text = getString(
                R.string.header_message_duel
            )
            binding.lDuelRewardContainer.tvAmountOfXpGained.text = getString(
                R.string.amount_of_xp_gained,
                it.xp
            )
            binding.lDuelRewardContainer.btnConfirm.setOnClickListener {
                viewModel.closeScreen()
            }
        }
    }

    private fun setupShouldCloseScreen() {
        viewModel.shouldCloseScreen.observe(requireActivity()){
            findNavController().navigate(
                R.id.action_duelFieldFragment_to_duelStatisticsFragment
            )
        }
    }

    private fun setupOnGiveUpListener() {
        binding.btnGiveUp.setOnClickListener {
            viewModel.cancelDuel()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startPeriodicalDataUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.stopPeriodicalDataUpdate()
        removeAllObservers()
        playerProfileImages.recycle()
        itemImgIds.recycle()
    }

    private fun removeAllObservers() {
        if(!this::viewModel.isInitialized) return
        viewModel.duelField.removeObservers(requireActivity())
        viewModel.finishedDuelReward.removeObservers(requireActivity())
        viewModel.shouldCloseScreen.removeObservers(requireActivity())
    }

    companion object {

        fun newFragment(): DuelFieldFragment {
            return DuelFieldFragment()
        }
    }
}