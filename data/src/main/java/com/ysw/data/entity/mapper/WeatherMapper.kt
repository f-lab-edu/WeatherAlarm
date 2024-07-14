package com.ysw.data.entity.mapper

import com.ysw.data.source.models.WeatherData
import com.ysw.domain.models.DomainWeather

object WeatherApiMapper : EntityMapper<DomainWeather, WeatherData> {


    override fun asDomain(entity: WeatherData): DomainWeather {
        return DomainWeather(
            id = entity.weather.first().id
        )
    }

    override fun asEntity(domain: DomainWeather): WeatherData {
        throw Exception("Not Use Function")
    }
}

fun WeatherData.asDomain() : DomainWeather = WeatherApiMapper.asDomain(this)