package com.puj.stepsfitnessgame.data.network.guildenterrequest

import com.puj.stepsfitnessgame.domain.models.guild.GuildEnterRequest
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GuildEnterRequestApiService {

    @POST("guild_enter_request/send_guild_request/{guild_id}")
    suspend fun sendGuildRequest(@Header("token") token: String, @Path("guild_id") guildId: Long)

    @DELETE("guild_enter_request/accept_guild_request/{request_id}")
    suspend fun acceptGuildRequest(@Header("token") token: String, @Path("request_id") requestId: Long)

    @DELETE("guild_enter_request/cancel_guild_request/{guild_id}")
    suspend fun cancelGuildRequest(@Header("token") token: String, @Path("guild_id") guildId: Long)

    @DELETE("guild_enter_request/refuse_guild_request/{request_id}")
    suspend fun refuseGuildRequest(@Header("token") token: String, @Path("request_id") requestId: Long)

    @GET("guild_enter_request/get_guild_enter_requests")
    suspend fun getGuildEnterRequests(@Header("token") token: String): Response<List<GuildEnterRequest>>
}