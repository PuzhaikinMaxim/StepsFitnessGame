package com.puj.stepsfitnessgame.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puj.stepsfitnessgame.data.network.guildenterrequest.GuildEnterRequestDataSourceImpl
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest
import com.puj.stepsfitnessgame.domain.repositories.GuildEnterRequestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuildEnterRequestRepositoryImpl(
    sharedPreferences: SharedPreferences
): GuildEnterRequestRepository {

    private val token: String = sharedPreferences.getString(
        TOKEN_KEY,
        TOKEN_DEFAULT
    ) ?: TOKEN_DEFAULT

    private val guildEnterRequestDataSource = GuildEnterRequestDataSourceImpl(token)

    private val guildEnterRequests = MutableLiveData<List<GuildEnterRequest>>()

    override suspend fun requestEnter(guildId: Long) {
        guildEnterRequestDataSource.requestEnter(guildId)
    }

    override suspend fun cancelEnter(guildId: Long) {
        guildEnterRequestDataSource.cancelEnter(guildId)
    }

    override fun getGuildEnterRequests(): LiveData<List<GuildEnterRequest>> {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = guildEnterRequestDataSource.getGuildEnterRequests()
            if(response is Response.Success){
                guildEnterRequests.postValue(response.data)
            }
        }
        return guildEnterRequests
    }

    override suspend fun refuseEnter(requestId: Long) {
        guildEnterRequestDataSource.refuseEnter(requestId)
    }

    override suspend fun acceptEnter(requestId: Long) {
        guildEnterRequestDataSource.acceptEnter(requestId)
    }

    companion object {
        private const val TOKEN_KEY = "authToken"
        private const val TOKEN_DEFAULT = "default"
    }
}