package com.puj.stepsfitnessgame.data.network.guild

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GuildApiService {

    @POST("guild/create_guild")
    fun createGuild(@Header("token") token: String, @Body guildDataDto: GuildDataDto)

    @PUT("guild/expel_player/{expelled_player_id}")
    fun expelPlayer(
        @Header("token") token: String,
        @Path(value = "expelled_player_id") expelledPlayerId: Long
    )

    @GET("guild/get_guild_list")
    fun getGuildList(@Header("token") token: String)

    @GET("guild/get_guild_data")
    fun getGuildData(@Header("token") token: String)

    @GET("guild/get_guild_statistics")
    fun getGuildStatistics(@Header("token") token: String)
}