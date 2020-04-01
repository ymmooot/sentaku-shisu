package dev.ymmooot

import java.time.LocalDate

class WeatherForecast(
    val date: LocalDate,
    val weather: Weather,
    val washingIndex: WashingIndex,
    val advise: String,
    val chanceOfRain: Int,
    val maxTemperature: Int,
    val minTemperature: Int
)
