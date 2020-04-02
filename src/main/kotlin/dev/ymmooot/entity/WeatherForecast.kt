package dev.ymmooot.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherForecast(
        val date: LocalDate,
        val weather: Weather,
        val laundryIndex: LaundryIndex,
        val advise: String,
        val chanceOfRain: Int,
        val maxTemperature: Int,
        val minTemperature: Int,
        val areaCode: String,
        val areaName: String,
        val publishedAt: LocalDateTime
)
