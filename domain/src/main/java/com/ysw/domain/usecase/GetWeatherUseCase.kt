package com.ysw.domain.usecase

import com.ysw.domain.repository.NetworkRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor (private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(lat: Double, lon: Double) = networkRepository.getWeather(lat, lon)
}