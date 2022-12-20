package com.puj.stepsfitnessgame.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.data.network.UserRemoteDataSource
import com.puj.stepsfitnessgame.databinding.ActivityMainBinding
import com.puj.stepsfitnessgame.domain.models.user.UserCredentials
import com.puj.stepsfitnessgame.presentation.fragments.AuthFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startFragment(AuthFragment.newFragment())
        val source = UserRemoteDataSource()
        scope.launch {
            try {
                source.test()
            }
            catch (ex: SocketTimeoutException) {

            }
        }
    }

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fc_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}