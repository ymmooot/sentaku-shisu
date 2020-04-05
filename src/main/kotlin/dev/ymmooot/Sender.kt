package dev.ymmooot

import dev.ymmooot.viewmodels.Body
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

class Sender(private val endpoint: URI) {
    fun send(body: Body): String {
        val bodyString = body.toJson()
        val request = HttpRequest.newBuilder()
                .uri(this.endpoint)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                .build()
        val bodyHandler = HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
        val response: HttpResponse<String> = HttpClient.newBuilder().build().send(request, bodyHandler)

        return response.body()
    }
}
