package dev.ymmooot

fun main(args: Array<String>) {
    val scraper = Scraper("3/16/4410/13114/")
    val f = scraper.fetchWashingIndex()
    f.forEach { println(it.weather) }
}
