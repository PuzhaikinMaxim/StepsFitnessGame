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
import com.puj.stepsfitnessgame.databinding.FragmentRegistrationBinding
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.viewmodels.AuthViewModel
import com.puj.stepsfitnessgame.presentation.viewmodels.RegistrationViewModel

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding
        get() = _binding ?: throw RuntimeException("Binding is null")

    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: throw RuntimeException()
        viewModel =
            ViewModelProvider(
                this, ViewModelFactory(sharedPref)
            )[RegistrationViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        setOnClickListeners()
    }

    private fun observeLiveData() {
        viewModel.isRegistered.observe(requireActivity()){
            println("Registration completed")
        }

        viewModel.isRegistrationError.observe(requireActivity()){
            Toast.makeText(
                requireActivity(), "Пользователь уже существует", Toast.LENGTH_SHORT
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
            val usernameInput = binding.etUsername.text.toString()
            val emailInput = binding.etEmail.text.toString()
            val passwordInput = binding.etPassword.text.toString()
            viewModel.registerUser(usernameInput, emailInput, passwordInput)
        }

        binding.tvGoToAuth.setOnClickListener {
            startFragment(AuthFragment.newFragment())
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

        fun newFragment(): RegistrationFragment {
            return RegistrationFragment()
        }
    }
}