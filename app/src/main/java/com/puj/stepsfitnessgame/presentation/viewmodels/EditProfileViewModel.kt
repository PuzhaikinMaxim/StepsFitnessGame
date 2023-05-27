package com.puj.stepsfitnessgame.presentation.viewmodels

import android.content.SharedPreferences
import android.content.res.TypedArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puj.stepsfitnessgame.data.repositories.UserDataRepositoryImpl
import com.puj.stepsfitnessgame.data.repositories.UserRepositoryImpl
import com.puj.stepsfitnessgame.domain.models.profileimage.ProfileImage
import com.puj.stepsfitnessgame.domain.models.userdata.UserEditProfileData
import com.puj.stepsfitnessgame.domain.usecases.user.ChangeUsernameUseCase
import com.puj.stepsfitnessgame.domain.usecases.user.SetProfileImageUseCase
import com.puj.stepsfitnessgame.domain.usecases.userdata.GetUserEditProfileDataUseCase
import com.puj.stepsfitnessgame.presentation.InputValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel(sharedPreferences: SharedPreferences): ViewModel() {

    private val userRepository = UserRepositoryImpl(sharedPreferences)

    private val userDataRepository = UserDataRepositoryImpl(sharedPreferences)

    private val getUserEditProfileDataUseCase = GetUserEditProfileDataUseCase(userDataRepository)

    private val changeUsernameUseCase = ChangeUsernameUseCase(userRepository)

    private val setProfileImageUseCase = SetProfileImageUseCase(userRepository)

    private val _userEditProfileData = MutableLiveData<UserEditProfileData>()
    val userEditProfileData: LiveData<UserEditProfileData>
        get() = _userEditProfileData

    private val _profileImageList = MutableLiveData<List<ProfileImage>>()
    val profileImageList: LiveData<List<ProfileImage>>
        get() = _profileImageList

    private val _isUsernameChanged = MutableLiveData<Unit>()
    val isUsernameChanged: LiveData<Unit>
        get() = _isUsernameChanged

    private val _isUserAlreadyExists = MutableLiveData<Boolean>()
    val isUserAlreadyExists: LiveData<Boolean>
        get() = _isUserAlreadyExists

    fun getUserProfileData() {
        viewModelScope.launch(Dispatchers.IO) {
            val profileEditData = getUserEditProfileDataUseCase() ?: return@launch
            selectProfileImage(profileEditData.imageId)
            _userEditProfileData.postValue(profileEditData)
        }
    }

    fun changeUsername(username: String) {
        val validator = getUsernameValidator(username)
        if(!validator.validate().isValid) return
        viewModelScope.launch(Dispatchers.IO) {
            val isChanged = changeUsernameUseCase(username)
            if(!isChanged){
                _isUserAlreadyExists.postValue(true)
                return@launch
            }
            _isUsernameChanged.postValue(Unit)
        }
    }

    fun setProfileImage(profileImageId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val isChanged = setProfileImageUseCase(profileImageId)
            if(!isChanged) return@launch
            selectProfileImage(profileImageId)
        }
    }

    fun resetUserAlreadyExists() {
        _isUserAlreadyExists.value = false
    }

    fun createProfileImageList(profileImageResourceIds: TypedArray) {
        val profileImageList = ArrayList<ProfileImage>()
        var index = 0
        while (profileImageResourceIds.getResourceId(index, -1) != -1){
            val profileImageId = profileImageResourceIds.getResourceId(index, -1)
            val guildLogo = ProfileImage(
                profileImageId,
                index,
                false
            )
            profileImageList.add(guildLogo)
            index++
        }
        _profileImageList.value = profileImageList
    }

    private fun selectProfileImage(profileImageId: Int) {
        val newProfileImageList = mutableListOf<ProfileImage>()
        _profileImageList.value?.forEach {
            newProfileImageList.add(it.copy(isSelected = false))
        }
        newProfileImageList[profileImageId] = newProfileImageList[profileImageId].copy(isSelected = true)
        _profileImageList.postValue(newProfileImageList)
    }

    private fun getUsernameValidator(username: String): InputValidator {
        val usernameValidator = InputValidator(username)
        return usernameValidator
            .isNotBlank()
            .minSymbols(6)
            .maxSymbols(30)
    }
}