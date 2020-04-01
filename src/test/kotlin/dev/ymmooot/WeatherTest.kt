package dev.ymmooot

import java.net.URL
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WeatherTest {

    @Test
    fun testImage() {
        assertEquals(URL("https://static.tenki.jp/images/icon/forecast-days-weather/01.png"), Weather.SUNNY.image)
        assertEquals(URL("https://static.tenki.jp/images/icon/forecast-days-weather/03.png"), Weather.SUNNY.sometimes(Weather.RAINY).image)
        assertEquals(URL("https://static.tenki.jp/images/icon/forecast-days-weather/29.png"), Weather.SNOW.then(Weather.RAINY).image)
        assertEquals(URL("https://static.tenki.jp/images/icon/forecast-days-weather/30.png"), Weather.HEAVY_SNOW.image)
    }

    @Test
    fun testCode() {
        assertEquals(1, Weather.SUNNY.code)
        assertEquals(2, Weather.SUNNY.sometimes(Weather.CLOUDY).code)
        assertEquals(3, Weather.SUNNY.sometimes(Weather.RAINY).code)
        assertEquals(4, Weather.SUNNY.sometimes(Weather.SNOW).code)
        assertEquals(5, Weather.SUNNY.then(Weather.CLOUDY).code)
        assertEquals(6, Weather.SUNNY.then(Weather.RAINY).code)
        assertEquals(7, Weather.SUNNY.then(Weather.SNOW).code)
        assertEquals(8, Weather.CLOUDY.code)
        assertEquals(9, Weather.CLOUDY.sometimes(Weather.SUNNY).code)
        assertEquals(10, Weather.CLOUDY.sometimes(Weather.RAINY).code)
        assertEquals(11, Weather.CLOUDY.sometimes(Weather.SNOW).code)
        assertEquals(12, Weather.CLOUDY.then(Weather.SUNNY).code)
        assertEquals(13, Weather.CLOUDY.then(Weather.RAINY).code)
        assertEquals(14, Weather.CLOUDY.then(Weather.SNOW).code)
        assertEquals(15, Weather.RAINY.code)
        assertEquals(16, Weather.RAINY.sometimes(Weather.SUNNY).code)
        assertEquals(17, Weather.RAINY.sometimes(Weather.CLOUDY).code)
        assertEquals(18, Weather.RAINY.sometimes(Weather.SNOW).code)
        assertEquals(19, Weather.RAINY.then(Weather.SUNNY).code)
        assertEquals(20, Weather.RAINY.then(Weather.CLOUDY).code)
        assertEquals(21, Weather.RAINY.then(Weather.SNOW).code)
        assertEquals(22, Weather.HEAVY_RAINY.code)
        assertEquals(23, Weather.SNOW.code)
        assertEquals(24, Weather.SNOW.sometimes(Weather.SUNNY).code)
        assertEquals(25, Weather.SNOW.sometimes(Weather.CLOUDY).code)
        assertEquals(26, Weather.SNOW.sometimes(Weather.RAINY).code)
        assertEquals(27, Weather.SNOW.then(Weather.SUNNY).code)
        assertEquals(28, Weather.SNOW.then(Weather.CLOUDY).code)
        assertEquals(29, Weather.SNOW.then(Weather.RAINY).code)
        assertEquals(30, Weather.HEAVY_SNOW.code)
    }

    @Test
    fun `can not combine same weather`() {
        val exception = assertThrows<IllegalArgumentException> ("Should throw an exception") {
            Weather.SUNNY.sometimes(Weather.SUNNY)
        }
        assertEquals("before and after must not be same weather", exception.message)
    }

    @Test
    fun estInfixFunctions() {
        assertEquals(Weather.SNOW.sometimes(Weather.SUNNY).code, (Weather.SNOW sometimes Weather.SUNNY).code)
        assertEquals(Weather.SNOW.then(Weather.SUNNY).code, (Weather.SNOW then Weather.SUNNY).code)
    }
}
