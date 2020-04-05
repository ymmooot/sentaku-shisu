package dev.ymmooot.viewmodels

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.ymmooot.entity.WeatherForecast

class Body(
        @JsonIgnore val areaName: String,
        val attachments: List<Attachment>,
        val iconUrl: String? = null
) {
    constructor(forecasts: List<WeatherForecast>, iconURL: String? = null) : this(forecasts.first().areaName, forecasts.map(::Attachment), iconURL)

    val username = "洗濯指数お知らせくん"
    val text = "${areaName}の洗濯指数をお知らせするよ"

    private val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    }

    fun toJson(): String = this.objectMapper.writeValueAsString(this)
}
