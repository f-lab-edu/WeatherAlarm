package com.ysw.presentation.compose

enum class WeatherCondition(val description: String, val musicUri: String) {
    CLEAR("맑음","/android.resource://com.ysw.presentation/raw/default_sunny"),
    RAIN("비","/android.resource://com.ysw.presentation/raw/default_rain"),
    SNOW("눈","/android.resource://com.ysw.presentation/raw/default_snow")
}