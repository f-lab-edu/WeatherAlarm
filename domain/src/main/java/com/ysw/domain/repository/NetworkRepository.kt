package com.ysw.domain.repository

import com.ysw.domain.models.DomainWeather


interface NetworkRepository {
    suspend fun getWeather(lat: Double, lon: Double): DomainWeather
}