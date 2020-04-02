package dev.ymmooot.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.URL

class WeatherTest {

    @Test
    fun testImage() {
        assertThat(Weather.SUNNY.image).isEqualTo(URL("https://static.tenki.jp/images/icon/forecast-days-weather/01.png"))
        assertThat(Weather.SUNNY.sometimes(Weather.RAINY).image).isEqualTo(URL("https://static.tenki.jp/images/icon/forecast-days-weather/03.png"))
        assertThat(Weather.SNOW.then(Weather.RAINY).image).isEqualTo(URL("https://static.tenki.jp/images/icon/forecast-days-weather/29.png"))
        assertThat(Weather.HEAVY_SNOW.image).isEqualTo(URL("https://static.tenki.jp/images/icon/forecast-days-weather/30.png"))
    }

    @Test
    fun testCode() {
        assertThat(Weather.SUNNY.code).isEqualTo(1)
        assertThat(Weather.SUNNY.sometimes(Weather.CLOUDY).code).isEqualTo(2)
        assertThat(Weather.SUNNY.sometimes(Weather.RAINY).code).isEqualTo(3)
        assertThat(Weather.SUNNY.sometimes(Weather.SNOW).code).isEqualTo(4)
        assertThat(Weather.SUNNY.then(Weather.CLOUDY).code).isEqualTo(5)
        assertThat(Weather.SUNNY.then(Weather.RAINY).code).isEqualTo(6)
        assertThat(Weather.SUNNY.then(Weather.SNOW).code).isEqualTo(7)
        assertThat(Weather.CLOUDY.code).isEqualTo(8)
        assertThat(Weather.CLOUDY.sometimes(Weather.SUNNY).code).isEqualTo(9)
        assertThat(Weather.CLOUDY.sometimes(Weather.RAINY).code).isEqualTo(10)
        assertThat(Weather.CLOUDY.sometimes(Weather.SNOW).code).isEqualTo(11)
        assertThat(Weather.CLOUDY.then(Weather.SUNNY).code).isEqualTo(12)
        assertThat(Weather.CLOUDY.then(Weather.RAINY).code).isEqualTo(13)
        assertThat(Weather.CLOUDY.then(Weather.SNOW).code).isEqualTo(14)
        assertThat(Weather.RAINY.code).isEqualTo(15)
        assertThat(Weather.RAINY.sometimes(Weather.SUNNY).code).isEqualTo(16)
        assertThat(Weather.RAINY.sometimes(Weather.CLOUDY).code).isEqualTo(17)
        assertThat(Weather.RAINY.sometimes(Weather.SNOW).code).isEqualTo(18)
        assertThat(Weather.RAINY.then(Weather.SUNNY).code).isEqualTo(19)
        assertThat(Weather.RAINY.then(Weather.CLOUDY).code).isEqualTo(20)
        assertThat(Weather.RAINY.then(Weather.SNOW).code).isEqualTo(21)
        assertThat(Weather.HEAVY_RAINY.code).isEqualTo(22)
        assertThat(Weather.SNOW.code).isEqualTo(23)
        assertThat(Weather.SNOW.sometimes(Weather.SUNNY).code).isEqualTo(24)
        assertThat(Weather.SNOW.sometimes(Weather.CLOUDY).code).isEqualTo(25)
        assertThat(Weather.SNOW.sometimes(Weather.RAINY).code).isEqualTo(26)
        assertThat(Weather.SNOW.then(Weather.SUNNY).code).isEqualTo(27)
        assertThat(Weather.SNOW.then(Weather.CLOUDY).code).isEqualTo(28)
        assertThat(Weather.SNOW.then(Weather.RAINY).code).isEqualTo(29)
        assertThat(Weather.HEAVY_SNOW.code).isEqualTo(30)
    }

    @Test
    fun `can not combine same weather`() {
        val exception = assertThrows<IllegalArgumentException>("Should throw an exception") {
            Weather.SUNNY.sometimes(Weather.SUNNY)
        }
        assertThat(exception.message).isEqualTo("before and after must not be same weather")
    }

    @Test
    fun testInfixFunctions() {
        assertThat(Weather.SNOW sometimes Weather.SUNNY).isEqualTo(
                Weather.SNOW.sometimes(Weather.SUNNY))
        assertThat(Weather.SNOW.then(Weather.SUNNY)).isEqualTo(
                Weather.SNOW then Weather.SUNNY)
    }

    @Test
    fun testFromCode() {
        assertThat(Weather.fromCode(1)).isEqualTo(Weather.SUNNY)
        assertThat(Weather.fromCode(24)).isEqualTo(
                Weather.SNOW.sometimes(
                        Weather.SUNNY))
        assertThat(Weather.fromCode(27)).isEqualTo(
                Weather.SNOW.then(
                        Weather.SUNNY))
    }
}
