package com.puj.stepsfitnessgame.data.network.guild

import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.data.network.challenge.CompletedChallengeRewardDto
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.guild.GuildEditionInfo
import com.puj.stepsfitnessgame.domain.models.guild.GuildData
import com.puj.stepsfitnessgame.domain.models.guild.GuildParticipant
import com.puj.stepsfitnessgame.domain.models.guild.GuildStatistics

class GuildDataProviderImpl(private val token: String): GuildDataProvider {

    private val service = ServiceFactory.create(GuildApiService::class.java)

    override suspend fun getGuildList(): Response<List<GuildListItemDto>> {
        try {
            val response = service.getGuildList(token)
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

    override suspend fun getGuildData(): Response<GuildData> {
        try {
            val response = service.getGuildData(token)
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

    override suspend fun getGuildStatistics(): Response<GuildStatistics> {
        try {
            val response = service.getGuildStatistics(token)
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

    override suspend fun getGuildParticipants(): Response<List<GuildParticipant>> {
        try {
            val response = service.getGuildParticipants(token)
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

    override suspend fun createGuild(guildEditionInfo: GuildEditionInfo) {
        try {
            service.createGuild(token, guildEditionInfo)
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    override suspend fun expelGuildParticipant(guildParticipantId: Long) {
        try {
            service.expelPlayer(token, guildParticipantId)
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    override suspend fun claimReward(): Response<CompletedChallengeRewardDto?> {
        try {
            val response = service.claimReward(token)
            if(response.isSuccessful){
                return Response.Success(response.body())
            }
            return Response.Error(AppErrorCodes.DEFAULT_ERROR_CODE)
        }
        catch (ex: Exception){
            ex.printStackTrace()
            return Response.Error(AppErrorCodes.SERVER_NOT_RESPONDING_CODE)
        }
    }

    override suspend fun getHasReward(): Response<Boolean> {
        try {
            val response = service.getHasReward(token)
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

    override suspend fun getIsOwner(): Response<Boolean> {
        try {
            val response = service.getIsOwner(token)
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

    override suspend fun editGuild(guildEditionInfo: GuildEditionInfo) {
        try {
            service.editGuildData(token,guildEditionInfo)
        }
        catch (_: Exception){

        }
    }

    override suspend fun getGuildEditionInfo(): Response<GuildEditionInfo> {
        try {
            val response = service.getGuildEditionInfo(token)
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
}