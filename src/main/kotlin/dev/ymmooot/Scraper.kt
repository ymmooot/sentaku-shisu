package dev.ymmooot

import dev.ymmooot.entity.LaundryIndex
import dev.ymmooot.entity.Weather
import dev.ymmooot.entity.WeatherForecast
import org.jsoup.Jsoup
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Scraper(var areaCode: String) {
    private val endpointBase = "https://tenki.jp/indexes/cloth_dried/"
    private val endpoint: URI
        get() = URI.create("$endpointBase$areaCode")

    fun fetchForecast(onlyTomorrow: Boolean): List<WeatherForecast> {
        val request = HttpRequest
                .newBuilder(this.endpoint)
                .build()
        val bodyHandler = HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
        val response: HttpResponse<String> = HttpClient.newBuilder().build().send(request, bodyHandler)
        val html: String = response.body()

        val areaName = extractAreaName(html)
        val publishedAt = extractPublishedAt(html)

        return if (onlyTomorrow) {
            tomorrowForecast(html, areaName, publishedAt)
        } else {
            todayAndTomorrowForecast(html, areaName, publishedAt)
        }
    }

    private fun todayAndTomorrowForecast(html: String, areaName: String, publishedAt: LocalDateTime): List<WeatherForecast> =
            listOf(".today-weather", ".tomorrow-weather")
                    .map { convertLargeSection(html, it, areaName, publishedAt) }

    private fun tomorrowForecast(html: String, areaName: String, publishedAt: LocalDateTime): List<WeatherForecast> =
            listOf(convertLargeSection(html, ".tomorrow-weather", areaName, publishedAt))

    private fun extractAreaName(html: String): String =
            Jsoup.parse(html)
                    .select("h2")
                    .text()
                    .let {
                        Regex("(?<area>.+)の洗濯指数").find(it)?.groups?.get("area")?.value
                    } ?: throw Exception("area name was not found")

    private fun extractPublishedAt(html: String): LocalDateTime =
            Jsoup.parse(html)
                    .select("h2>.date-time")
                    .attr("datetime")
                    .let {
                        LocalDateTime.parse(it, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    } ?: throw Exception("published time was not found")

    private fun convertLargeSection(html: String, selector: String, areaName: String, publishedAt: LocalDateTime): WeatherForecast {
        val section = Jsoup.parse(html).select(selector)
        val weather = section
                .select(".weather-icon-box>img")
                .attr("src")
                .let {
                    Regex("forecast-days-weather/(?<index>.+)?.png").find(it)?.groups?.get("index")?.value
                }
                ?.removeSuffix("_n")
                ?.let(Integer::parseInt)
                ?.let { Weather.fromCode(it) } ?: throw Exception("failed to extract weather from HTML")

        val date = section
                .select(".left-style")
                .text()
                .let {
                    Regex(" (?<dateString>.+)\\(").find(it)?.groups?.get("dateString")?.value
                }
                ?.let {
                    LocalDate.parse("${LocalDate.now().year}年$it", DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
                } ?: throw Exception("failed to extract date from HTML")

        val laundryIndex = section
                .select(".indexes-icon-box img")
                .attr("src")
                .let {
                    Regex("/icon-large-(?<index>\\d).png").find(it)?.groups?.get("index")?.value
                }
                ?.let(Integer::parseInt)
                ?.let { LaundryIndex.fromInt(it) } ?: throw Exception("failed to extract laundry index from HTML")

        return WeatherForecast(
                date = date,
                weather = weather,
                laundryIndex = laundryIndex,
                maxTemperature = section.select(".high-temp").text().removeSuffix("℃").toInt(),
                minTemperature = section.select(".low-temp").text().removeSuffix("℃").toInt(),
                chanceOfRain = section.select(".precip").text().removeSuffix("%").toInt(),
                advise = section.select(".indexes-telop-1").text(),
                areaCode = this.areaCode,
                areaName = areaName,
                publishedAt = publishedAt
        )
    }
}
