package com.puj.stepsfitnessgame.data.network.rating

import com.puj.stepsfitnessgame.data.network.guild.GuildDataDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RatingApiService {

    @GET("players_rating/get_top_players_by_step_amount")
    suspend fun getTopOneHundredPlayersByStepAmount(@Header("token") token: String): Response<List<RatingDto>>

    @GET("players_rating/get_top_players_by_amount_of_duels_won")
    suspend fun getTopOneHundredPlayersByAmountOfDuelsWon(@Header("token") token: String): Response<List<RatingDto>>

    @GET("players_rating/get_rating_list_update_countdown")
    suspend fun getRatingListUpdateCountdown(@Header("token") token: String): Response<String>
}