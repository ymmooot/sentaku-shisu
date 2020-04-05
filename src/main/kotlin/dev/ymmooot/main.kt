package dev.ymmooot

import dev.ymmooot.entity.Env
import dev.ymmooot.viewmodels.Body
import java.net.URI
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val env = Env()
    val scraper = Scraper(env.areaCode)
    val sender = Sender(URI.create(env.endpointURL))
    val response = try {
        val forecasts = scraper.fetchForecast()
        val body = Body(forecasts, env.iconURL)
        sender.send(body)
    } catch (e: Exception) {
        println(e)
        exitProcess(1)
    }

    println(response)
}
