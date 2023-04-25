package com.puj.stepsfitnessgame.data.network.guild

import com.puj.stepsfitnessgame.data.network.challenge.CompletedChallengeRewardDto
import com.puj.stepsfitnessgame.domain.models.guild.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GuildApiService {

    @POST("guild/create_guild")
    suspend fun createGuild(@Header("token") token: String, @Body guildCreationInfo: GuildCreationInfo)

    @PUT("guild/expel_player/{expelled_player_id}")
    suspend fun expelPlayer(
        @Header("token") token: String,
        @Path(value = "expelled_player_id") expelledPlayerId: Long
    )

    @GET("guild/get_guild_list")
    suspend fun getGuildList(@Header("token") token: String): Response<List<GuildListItemDto>>

    @GET("guild/get_guild_data")
    suspend fun getGuildData(@Header("token") token: String): Response<GuildData>

    @GET("guild/get_guild_statistics")
    suspend fun getGuildStatistics(@Header("token") token: String): Response<GuildStatistics>

    @GET("guild/get_guild_participants")
    suspend fun getGuildParticipants(@Header("token") token: String): Response<List<GuildParticipant>>

    @GET("guild/get_is_owner")
    suspend fun getIsOwner(@Header("token") token: String): Response<Boolean>

    @DELETE("guild_challenges_reward/claim_reward")
    suspend fun claimReward(@Header("token") token: String): Response<CompletedChallengeRewardDto>

    @GET("guild_challenges_reward/get_has_reward")
    suspend fun getHasReward(@Header("token") token: String): Response<Boolean>
}