package com.puj.stepsfitnessgame.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ServiceFactory {

    private const val BASE_URL = "http://192.168.1.195:8080/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    fun<T> create(service: Class<T>): T {
        return retrofit.create(service);
    }
}