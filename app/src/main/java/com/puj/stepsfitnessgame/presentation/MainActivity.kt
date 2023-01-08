package com.puj.stepsfitnessgame.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.data.network.user.UserRemoteDataSourceImpl
import com.puj.stepsfitnessgame.databinding.ActivityMainBinding
import com.puj.stepsfitnessgame.presentation.fragments.AuthFragment
import com.puj.stepsfitnessgame.presentation.fragments.ChallengeListFragment
import com.puj.stepsfitnessgame.presentation.viewmodels.AuthViewModel
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
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: throw RuntimeException()
        viewModel =
            ViewModelProvider(this, ViewModelFactory(sharedPref))[MainViewModel::class.java]
        setIsUserAuthorizedListener()
        viewModel.isUserLoggedIn()
        val source = UserRemoteDataSourceImpl()
        scope.launch {
            try {
                source.test()
            }
            catch (ex: SocketTimeoutException) {

            }
        }
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
        val intent = ContainerFragmentActivity.newIntent(this)
        startActivity(intent)
        finish()
    }
}