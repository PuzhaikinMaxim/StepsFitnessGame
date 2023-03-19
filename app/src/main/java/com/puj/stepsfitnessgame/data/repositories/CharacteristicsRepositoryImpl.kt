package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.characteristics.CharacteristicsRemoteDataSourceImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.characteristics.Characteristics
import com.puj.stepsfitnessgame.domain.repositories.CharacteristicsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacteristicsRepositoryImpl(
    sharedPreferences: SharedPreferences
): CharacteristicsRepository {

    private val token: String = sharedPreferences.getString(TOKEN_KEY, DEFAULT) ?: DEFAULT

    private val characteristicsRemoteDataSource = CharacteristicsRemoteDataSourceImpl(token)

    private val characteristics = MutableLiveData<Characteristics>()

    override fun getCharacteristics(): LiveData<Characteristics> {
        setCharacteristics()
        return characteristics
    }

    override suspend fun increaseEndurance() {
        characteristicsRemoteDataSource.increaseEndurance()
        setCharacteristics()
    }

    override suspend fun increaseStrength() {
        characteristicsRemoteDataSource.increaseStrength()
        setCharacteristics()
    }

    private fun setCharacteristics() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val response = characteristicsRemoteDataSource.getCharacteristics()
            if(response is Response.Success){
                characteristics.postValue(response.data)
            }
        }
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val DEFAULT = "default"
    }
}