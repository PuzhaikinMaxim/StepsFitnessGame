package com.puj.stepsfitnessgame.data.network.guildenterrequest

import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest

class GuildEnterRequestDataProviderImpl(private val token: String): GuildEnterRequestDataProvider {

    private val service = ServiceFactory.create(GuildEnterRequestApiService::class.java)

    override suspend fun requestEnter(guildId: Long) {
        try {
            service.sendGuildRequest(token, guildId)
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    override suspend fun cancelEnter(guildId: Long) {
        try {
            service.cancelGuildRequest(token, guildId)
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    override suspend fun getGuildEnterRequests(): Response<List<GuildEnterRequest>> {
        try {
            val response = service.getGuildEnterRequests(token)
            if(response.isSuccessful && response.body() != null){
                return Response.Success(response.body()!!)
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception){
            ex.printStackTrace()
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun refuseEnter(requestId: Long) {
        try {
            service.refuseGuildRequest(token, requestId)
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    override suspend fun acceptEnter(requestId: Long) {
        try {
            service.acceptGuildRequest(token, requestId)
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}