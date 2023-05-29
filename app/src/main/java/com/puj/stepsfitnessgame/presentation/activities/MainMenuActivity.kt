package com.puj.stepsfitnessgame.presentation.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.data.workers.StepCountingWorker
import com.puj.stepsfitnessgame.data.database.FitnessGameDatabase
import com.puj.stepsfitnessgame.data.network.duel.DuelStompClient
import com.puj.stepsfitnessgame.databinding.ActivityMenuContainerBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.MainMenuContainer
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.fragments.*
import com.puj.stepsfitnessgame.presentation.viewmodels.MainMenuViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainMenuActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityMenuContainerBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MainMenuViewModel

    private lateinit var userProfileImages: TypedArray

    //private var isOnBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences(
            PreferencesValues.PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(sharedPref)
        )[MainMenuViewModel::class.java]

        userProfileImages = resources.obtainTypedArray(R.array.profile_images)

        //openChallengeListFragment()

        setupGoogleSignIn()
        setupRequestPermission()
        setDrawerLayout()
        setupBottomNavigationMenu()
        setupUserLevel()

        /*
        val duelStompClient = DuelStompClient(
            "userf72afb76-8230-4587-ba04-f6c6639d8538",
            "user"
        )
        */

        val duelStompClient2 = DuelStompClient(
            "testtest95ddaf83-dbf0-479c-9f8b-73c6ee9e65ce",
        )

        duelStompClient2.initializeConnection("user2")

        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch {
            for(i in 0..50){
                duelStompClient2.tryFindGame()
            }
        }

        FitnessGameDatabase.initializeDatabase(this)

        val request = OneTimeWorkRequestBuilder<StepCountingWorker>()
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            "step_counting_work",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    private fun setupUserLevel() {
        val headerView = binding.navView.getHeaderView(0)
        val tvUserLevel = headerView.findViewById<TextView>(R.id.tv_user_level)
        val pbUserLevelProgress = headerView.findViewById<ProgressBar>(R.id.pb_user_level_progress)
        val tvAmountOfXp = headerView.findViewById<TextView>(R.id.tv_amount_of_xp)
        val tvUserName = headerView.findViewById<TextView>(R.id.tv_user_name)
        val ivUserImage = headerView.findViewById<ImageView>(R.id.iv_user_icon)

        viewModel.userData.observe(this){
            tvUserLevel.text = getString(
                R.string.user_level_text,
                it.level
            )
            pbUserLevelProgress.progress = it.progress
            tvAmountOfXp.text = getString(
                R.string.amount_of_xp_text,
                it.amountOfXp,
                it.amountToGain
            )
            tvUserName.text = it.username
            ivUserImage.setImageResource(userProfileImages.getResourceId(it.profileImageId, -1))
            binding.tbHeader.ivUserIcon.setImageResource(
                userProfileImages.getResourceId(it.profileImageId, -1)
            )
            binding.tbHeader.tvUserName.text = it.username
        }
    }

    private fun setupBottomNavigationMenu() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fc_main_fragments_container) as NavHostFragment

        binding.tbFooter.bnvFooter.setupWithNavController(navHostFragment.navController)
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

        val googleSignInAccount = GoogleSignIn.getAccountForExtension(
            applicationContext,
            fitnessOptions
        )

        if(!GoogleSignIn.hasPermissions(googleSignInAccount, fitnessOptions)){
            GoogleSignIn.requestPermissions(this,
                1001,
                googleSignInAccount,
                fitnessOptions
            )
        }
    }

    private fun setupRequestPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {

            }

        if(
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACTIVITY_RECOGNITION
                )
            }
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
                R.id.menu_characteristics -> {
                    val intent = CharacteristicsActivity.newIntent(this)
                    startActivity(intent)
                }
                R.id.menu_inventory -> {
                    val intent = InventoryActivity.newIntent(this)
                    startActivity(intent)
                }
                R.id.menu_achievements -> {
                    val intent = AchievementActivity.newIntent(this)
                    startActivity(intent)
                }
                R.id.menu_settings -> {
                    val intent = EditProfileActivity.newIntent(this)
                    startActivity(intent)
                }
                R.id.menu_exit -> {
                    viewModel.isUserLoggedOut.observe(this){
                        val intent = MainActivity.newIntent(this)
                        startActivity(intent)
                        finish()
                    }
                    viewModel.logOut()
                }
                else -> {}
            }
            closeDrawer()
            true
        }
    }

    private fun openChallengeListFragment() {
        openFragment(ChallengeListFragment.newFragment())
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fc_main_fragments_container, fragment)
            .addToBackStack(null)
            .commit()
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

    override fun onDestroy() {
        super.onDestroy()
        userProfileImages.recycle()
    }

    companion object {
        private const val RC_GOOGLE_SIGN_IN = 100

        fun newIntent(context: Context): Intent {
            return Intent(context, MainMenuActivity::class.java)
        }
    }
}