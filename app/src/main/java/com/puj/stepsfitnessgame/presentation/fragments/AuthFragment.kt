package com.puj.stepsfitnessgame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentAuthBinding
import com.puj.stepsfitnessgame.presentation.viewmodels.AuthViewModel

class AuthFragment: Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding
        get() = _binding ?: throw RuntimeException("Auth fragment binding is null")

    private val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            startFragment(RegistrationFragment.newFragment())
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.isAuthenticated.observe(requireActivity()){
            println("Auth completed")
        }

        viewModel.isAuthenticationError.observe(requireActivity()){
            println("Error has occurred")
        }
    }

    private fun startFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fc_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newFragment(): AuthFragment {
            return AuthFragment()
        }
    }
}