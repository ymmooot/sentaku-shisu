package dev.ymmooot.viewmodels

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.ymmooot.entity.WeatherForecast
import java.time.format.DateTimeFormatter
import java.util.*

data class Attachment(@JsonIgnore val forecast: WeatherForecast) {
    val color = forecast.laundryIndex.color
    val authorName: String = forecast.date.format(DateTimeFormatter.ofPattern("M月d日(E)", Locale.JAPANESE))
    val authorLink = "https://tenki.jp/indexes/cloth_dried/${forecast.areaCode}"
    val title = forecast.laundryIndex.toString()
    val text = forecast.advise
    val fields = listOf(
            Field(
                    "天気",
                    "${forecast.weather.japanese} ${forecast.weather.emoji}",
                    true
            ),
            Field(
                    "気温",
                    "${forecast.maxTemperature}℃ / ${forecast.minTemperature}℃",
                    true
            ),
            Field(
                    "降水確率",
                    "${forecast.chanceOfRain}%",
                    true
            )
    )
    val thumbUrl = forecast.laundryIndex.image
    val footer = "tenki.jp\n${forecast.publishedAt.format(DateTimeFormatter.ofPattern("d日H時"))} 発表"
    val footerIcon = "https://static.tenki.jp/images/icon/bookmark/tenkijp_bookmark_icon_114_114.png"
}
