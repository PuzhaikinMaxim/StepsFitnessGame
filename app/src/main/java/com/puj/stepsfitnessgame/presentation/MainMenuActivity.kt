package com.puj.stepsfitnessgame.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.fragment.app.Fragment
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.data.StepCountingWorker
import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase
import com.puj.stepsfitnessgame.data.network.stepactivity.GoogleFitDataProvider
import com.puj.stepsfitnessgame.databinding.ActivityMenuContainerBinding
import com.puj.stepsfitnessgame.presentation.fragments.ChallengeListFragment
import com.puj.stepsfitnessgame.presentation.fragments.StatisticsFragment
import java.util.concurrent.TimeUnit

class MainMenuActivity: AppCompatActivity(), MainMenuContainer {

    private val binding by lazy {
        ActivityMenuContainerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        openChallengeListFragment()

        setupGoogleSignIn()
        setupRequestPermission()
        setDrawerLayout()

        val provider = GoogleFitDataProvider()
        println(provider.getTodayStepCount())

        FitnessGameDatabase.getDatabase(this)

        val request = OneTimeWorkRequestBuilder<StepCountingWorker>()
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            "step_counting_work",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val gc = GoogleSignIn.getClient(this, gso)

        println(GoogleSignIn.getLastSignedInAccount(this)?.account)
        if(GoogleSignIn.getLastSignedInAccount(this) == null){
            val signInIntent = gc.signInIntent
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
        }
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
            .build()

        val googleSignInAccount = GoogleSignIn.getAccountForExtension(applicationContext, fitnessOptions)

        if(!GoogleSignIn.hasPermissions(googleSignInAccount, fitnessOptions)){
            GoogleSignIn.requestPermissions(this,1001,googleSignInAccount,fitnessOptions)
        }
    }

    private fun setupRequestPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {}

        if(
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        }
    }

    private fun setDrawerLayout() {
        val listener = object : SimpleDrawerListener() {
            override fun onDrawerOpened(drawerView: View) {
                invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                invalidateOptionsMenu()
            }
        }

        with(binding){
            tbHeader.ivUserIcon.setOnClickListener {
                if(!dlMainMenuContainer.isDrawerOpen(navView)){
                    dlMainMenuContainer.openDrawer(GravityCompat.START)
                }
                else {
                    dlMainMenuContainer.closeDrawer(GravityCompat.START)
                }
            }

            dlMainMenuContainer.addDrawerListener(listener)
        }

        setupSlideMenu()
    }

    private fun closeDrawer() {
        binding.dlMainMenuContainer.closeDrawer(GravityCompat.START)
    }

    private fun setupSlideMenu() {
        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_profile -> {
                    val intent = UserProfileActivity.newIntent(this)
                    startActivity(intent)
                }
                else -> {}
            }
            closeDrawer()
            true
        }
    }

    private fun openChallengeListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fc_main_fragments_container, ChallengeListFragment.newFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fc_main_fragments_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        }
        else {
            finishAffinity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_GOOGLE_SIGN_IN){
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            println(gso.account)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val RC_GOOGLE_SIGN_IN = 100

        fun newIntent(context: Context): Intent {
            return Intent(context, MainMenuActivity::class.java)
        }
    }

    override fun startNewScreen(screenId: Int) {
        when(screenId){
            MainMenuContainer.CHALLENGE_LIST_FRAGMENT_CODE -> openChallengeListFragment()
            MainMenuContainer.STATISTICS_FRAGMENT_CODE -> openFragment(
                StatisticsFragment.newFragment()
            )
        }
    }
}