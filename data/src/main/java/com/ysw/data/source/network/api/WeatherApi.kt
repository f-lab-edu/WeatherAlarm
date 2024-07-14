package com.ysw.data.source.network.api

import com.ysw.data.BuildConfig
import com.ysw.data.source.models.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.WEATHER_APP_KEY
    ): Response<WeatherData>

}