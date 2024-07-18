package com.ysw.data.source.network

import com.ysw.data.source.network.api.WeatherApi
import javax.inject.Inject

class NetworkWeatherDataSource @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getWeather(lat: Double, lon: Double) = weatherApi.getWeather(lat, lon)
}