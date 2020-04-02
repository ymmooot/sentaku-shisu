package dev.ymmooot.sender

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.ymmooot.entity.WeatherForecast
import dev.ymmooot.viewmodels.Attachment
import dev.ymmooot.viewmodels.Body

abstract class Sender {
    abstract fun send(forecasts: List<WeatherForecast>): String

    private val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    }

    protected fun toBodyJsonString(forecasts: List<WeatherForecast>): String =
        forecasts
            .map { Attachment(it) }
            .let {
                Body(forecasts.first().areaName, it)
            }
            .let(objectMapper::writeValueAsString)
}
