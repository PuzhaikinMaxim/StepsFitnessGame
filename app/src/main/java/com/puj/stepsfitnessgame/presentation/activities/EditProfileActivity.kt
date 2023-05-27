package com.puj.stepsfitnessgame.presentation.activities

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.puj.stepsfitnessgame.R
import com.puj.stepsfitnessgame.databinding.ActivityEditProfileBinding
import com.puj.stepsfitnessgame.presentation.PreferencesValues
import com.puj.stepsfitnessgame.presentation.ViewModelFactory
import com.puj.stepsfitnessgame.presentation.adapters.profileimage.ProfileImageListAdapter
import com.puj.stepsfitnessgame.presentation.viewmodels.EditProfileViewModel

class EditProfileActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: EditProfileViewModel

    private lateinit var profileImages: TypedArray

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
        )[EditProfileViewModel::class.java]
        profileImages = resources.obtainTypedArray(R.array.profile_images)
        viewModel.createProfileImageList(profileImages)

        setRecyclerViewSpanCount()
        setupProfileImagesAdapter()
        setupEditProfileDataObserver()
        setupChangeUsername()
    }

    private fun setupProfileImagesAdapter() {
        val adapter = ProfileImageListAdapter(this)
        viewModel.profileImageList.observe(this){
            adapter.profileImageList = it
        }
        adapter.onSelectProfileImage = {
            viewModel.setProfileImage(it)
        }
        binding.rvProfileImages.adapter = adapter
    }

    private fun setupEditProfileDataObserver() {
        viewModel.userEditProfileData.observe(this){
            binding.etUsername.setText(it.username)
        }
    }

    private fun setRecyclerViewSpanCount() {
        val layoutManager = binding.rvProfileImages.layoutManager
        if(layoutManager is GridLayoutManager){
            val displayMetrics = resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            layoutManager.spanCount = (dpWidth/90).toInt()
        }
    }

    private fun setupChangeUsername() {
        viewModel.isUsernameChanged.observe(this){
            Toast.makeText(
                this,
                "Имя пользователя было изменено",
                Toast.LENGTH_SHORT).show()
        }
        viewModel.isUserAlreadyExists.observe(this){
            binding.tvUsernameChangeMsg.visibility = View.VISIBLE
        }
        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetUserAlreadyExists()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.ivChangeUsername.setOnClickListener {
            viewModel.changeUsername(binding.etUsername.text.toString())
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, EditProfileActivity::class.java)
        }
    }
}