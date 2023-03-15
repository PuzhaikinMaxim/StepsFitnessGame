package com.puj.stepsfitnessgame.domain.repositories

import androidx.lifecycle.LiveData
import com.puj.stepsfitnessgame.domain.models.userdata.UserData

interface UserDataRepository {

    fun getUserData(): LiveData<UserData>
}