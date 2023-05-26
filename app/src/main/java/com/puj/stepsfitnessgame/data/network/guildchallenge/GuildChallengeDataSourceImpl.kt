package com.puj.stepsfitnessgame.data.network.guildchallenge

import com.puj.stepsfitnessgame.data.network.AppErrorCodes
import com.puj.stepsfitnessgame.data.network.ServiceFactory
import com.puj.stepsfitnessgame.data.network.StepCount
import com.puj.stepsfitnessgame.domain.models.Response
import com.puj.stepsfitnessgame.domain.models.guild.CurrentGuildChallenge
import com.puj.stepsfitnessgame.domain.models.guild.GuildChallenge

class GuildChallengeDataSourceImpl(private val token: String): GuildChallengeDataSource {

    private val service = ServiceFactory.create(GuildChallengeApiService::class.java)

    override suspend fun selectGuildChallenge(guildChallengeId: Long) {
        try {
            service.startGuildChallenge(token, guildChallengeId)
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    override suspend fun getGuildChallenges(): Response<List<GuildChallenge>> {
        try {
            val response = service.generateGuildChallenges(token)
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

    override suspend fun getCurrentGuildChallenge(): Response<CurrentGuildChallenge?> {
        try {
            val response = service.getCurrentGuildChallenge(token)
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

    override suspend fun updateProgress(stepCount: Int) {
        try {
            service.updateProgress(token, StepCount(stepCount))
        }
        catch (ex: Exception){

        }
    }
}