package dev.ymmooot.sender

import dev.ymmooot.entity.WeatherForecast
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

class DiscordAndSlack(private val endpoint: URI) : Sender() {
    override fun send(forecasts: List<WeatherForecast>): String {
        val body = this.toBodyJsonString(forecasts)
        val request = HttpRequest.newBuilder()
                .uri(this.endpoint)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build()
        val bodyHandler = HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
        val response: HttpResponse<String> = HttpClient.newBuilder().build().send(request, bodyHandler)

        return response.body()
    }
}
