package com.puj.stepsfitnessgame.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.FragmentAuthBinding
import com.puj.stepsfitnessgame.presentation.activities.MainActivity
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.AuthViewModel

class AuthFragment: Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding
        get() = _binding ?: throw RuntimeException("Auth fragment binding is null")

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: throw RuntimeException()
        viewModel =
            ViewModelProvider(this, ViewModelFactory(sharedPref))[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        setOnClickListeners()
    }

    private fun observeLiveData() {
        viewModel.isAuthenticated.observe(requireActivity()){
            println("Auth completed")
            val activity = requireActivity()
            if(activity is MainActivity) {
                activity.openMainScreen()
            }
        }

        viewModel.isAuthenticationError.observe(requireActivity()){
            Toast.makeText(
                requireActivity(), "Пользователь не обнаружен", Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.isInputWrong.observe(requireActivity()){
            Toast.makeText(
                requireActivity(), "Неверно введены данные", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setOnClickListeners() {
        binding.btnRegister.setOnClickListener {
            startFragment(RegistrationFragment.newFragment())
        }

        binding.btnAuthenticate.setOnClickListener {
            val usernameInput = binding.etUsername.text.toString()
            val passwordInput = binding.etPassword.text.toString()
            viewModel.loginUser(usernameInput, passwordInput)
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