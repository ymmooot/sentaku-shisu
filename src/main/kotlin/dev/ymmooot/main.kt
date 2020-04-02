package dev.ymmooot

import dev.ymmooot.sender.DiscordAndSlack
import java.net.URI
import kotlin.system.exitProcess

fun mustGetEnv(key: String): String =
        System.getenv(key) ?: throw Exception("environmental variables not found: $key")

fun main(args: Array<String>) {
    val scraper = Scraper(mustGetEnv("AREA_CODE"))
    val uri = mustGetEnv("SEND_URL")
    val sender = DiscordAndSlack(URI.create(uri))
    val response = try {
        val forecast = scraper.fetchForecast()
        sender.send(forecast)
    } catch (e: Exception) {
        println(e)
        exitProcess(1)
    }

    println(response)
}
