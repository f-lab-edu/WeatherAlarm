package com.ysw.data.repository

import com.ysw.data.source.network.NetworkWeatherDataSource
import com.ysw.data.di.AlarmAppDispatchers
import com.ysw.data.di.Dispatcher
import com.ysw.data.entity.mapper.asDomain
import com.ysw.domain.models.DomainWeather
import com.ysw.domain.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    val networkWeatherDataSource: NetworkWeatherDataSource,
    @Dispatcher(AlarmAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : NetworkRepository {

    override suspend fun getWeather(lat: Double, lon: Double): DomainWeather {
        return runWithDispatcher(ioDispatcher){
            val weatherResponse = networkWeatherDataSource.getWeather(lat, lon)
            if (weatherResponse.isSuccessful) {
                val weatherData = weatherResponse.body() ?: throw Exception("Response body is null")
                weatherData.asDomain()
            } else {
                throw Exception("Error: ${weatherResponse.errorBody()?.string()}")
            }
        }
    }

}