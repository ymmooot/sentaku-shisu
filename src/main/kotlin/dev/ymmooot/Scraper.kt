package dev.ymmooot

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.jsoup.Jsoup

class Scraper(var areaCode: String) {
    private val endpointBase = "https://tenki.jp/indexes/cloth_dried/"
    private val endpoint: URI
        get() = URI.create("$endpointBase$areaCode")

    fun fetchWashingIndex(): List<WeatherForecast> {
        val html: String = try {
            val request = HttpRequest
                .newBuilder(this.endpoint)
                .build()
            val bodyHandler = HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            val response: HttpResponse<String> = HttpClient.newBuilder().build().send(request, bodyHandler)
            response.body()
        } catch (e: Exception) {
            return listOf()
        }

        return todayAndTomorrowForecast(html)
    }

    private fun todayAndTomorrowForecast(html: String): List<WeatherForecast> =
        listOf(".today-weather", ".tomorrow-weather")
            .mapNotNull { convertLargeSection(html, it) }

    private fun convertLargeSection(html: String, selector: String): WeatherForecast? {
            val section = Jsoup.parse(html).select(selector)
            val weather = section
                .select(".weather-icon-box>img")
                .attr("src")
                .let {
                    Regex("forecast-days-weather/(?<index>.+)?.png").find(it)?.groups?.get("index")?.value
                }
                ?.removeSuffix("_n")
                ?.let(Integer::parseInt)
                ?.let { Weather.fromCode(it) } ?: return null
            val date = section
                .select(".left-style")
                .text()
                .let {
                    Regex(" (?<dateString>.+)\\(").find(it)?.groups?.get("dateString")?.value
                }
                ?.let {
                    LocalDate.parse("${LocalDate.now().year}年$it", DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
                } ?: return null
            val washingIndex = section
                .select(".indexes-icon-box img")
                .attr("src")
                .let {
                    Regex("/icon-large-(?<index>\\d).png").find(it)?.groups?.get("index")?.value
                }
                ?.let(Integer::parseInt)
                ?.let { WashingIndex.fromInt(it) } ?: return null

            return WeatherForecast(
                date = date,
                weather = weather,
                washingIndex = washingIndex,
                maxTemperature = section.select(".high-temp").text().removeSuffix("℃").toInt(),
                minTemperature = section.select(".low-temp").text().removeSuffix("℃").toInt(),
                chanceOfRain = section.select(".precip").text().removeSuffix("%").toInt(),
                advise = section.select(".indexes-telop-1").text()
            )
        }
    }
