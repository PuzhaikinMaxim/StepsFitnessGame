package com.puj.stepsfitnessgame.presentation

import android.Manifest
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.data.network.stepactivity.GoogleFitDataProvider
import com.puj.stepsfitnessgame.databinding.ActivityMenuContainerBinding
import com.puj.stepsfitnessgame.presentation.fragments.ChallengeListFragment

class MainMenuActivity: AppCompatActivity() {

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

    }

    private fun openChallengeListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fc_main_fragments_container, ChallengeListFragment.newFragment())
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
}