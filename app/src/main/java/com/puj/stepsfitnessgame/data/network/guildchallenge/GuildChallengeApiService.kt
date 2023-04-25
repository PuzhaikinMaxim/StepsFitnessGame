package com.puj.stepsfitnessgame.data.network.guildchallenge

import com.puj.stepsfitnessgame.domain.models.guild.CurrentGuildChallenge
import com.puj.stepsfitnessgame.domain.models.guild.GuildChallenge
import retrofit2.Response
import retrofit2.http.*

interface GuildChallengeApiService {

    @PUT("guild_challenge/start_guild_challenge/{challenge_id}")
    suspend fun startGuildChallenge(@Header("token") token: String, @Path("challenge_id") challengeId: Long)

    @POST("guild_challenge/generate_guild_challenges")
    suspend fun generateGuildChallenges(
        @Header("token") token: String
    ): Response<List<GuildChallenge>>

    @GET("guild_challenge/get_current_guild_challenge")
    suspend fun getCurrentGuildChallenge(
        @Header("token") token: String
    ): Response<CurrentGuildChallenge>

    //@GET("guild_challenge/get_guild_challenges")
}