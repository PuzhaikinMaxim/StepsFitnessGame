package com.puj.stepsfitnessgame.presentation.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.data.network.duel.DuelStompClient
import com.puj.stepsfitnessgame.data.network.user.UserRemoteDataSourceImpl
import com.puj.stepsfitnessgame.databinding.ActivityMainBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.fragments.AuthFragment
import com.puj.stepsfitnessgame.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val sharedPref =
            getSharedPreferences(
                PreferencesValues.PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ) ?: throw RuntimeException()
        viewModel =
            ViewModelProvider(this, ViewModelFactory(sharedPref))[MainViewModel::class.java]
        setIsUserAuthorizedListener()
        viewModel.isUserLoggedIn()
    }

    private fun setIsUserAuthorizedListener() {
        viewModel.isUserLoggedIn.observe(this){
            when(it){
                true -> {
                    openMainScreen()
                }
                false -> {
                    startFragment(AuthFragment.newFragment())
                }
            }
        }
    }

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fc_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun openMainScreen() {
        val intent = MainMenuActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    fun showAccountCreatedToast() {
        Toast.makeText(
            this,
            "Учетная запись была успешно создана!",
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {

        fun newIntent(context: Context): Intent{
            return Intent(context, MainActivity::class.java)
        }
    }
}