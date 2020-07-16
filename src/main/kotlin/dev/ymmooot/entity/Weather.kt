package dev.ymmooot.entity

import java.net.URL

sealed class Weather {
    companion object {
        val SUNNY = Composable.SUNNY
        val CLOUDY = Composable.CLOUDY
        val SNOW = Composable.SNOW
        val RAINY = Composable.RAINY

        val values: List<Weather> by lazy {
            val composables = listOf(
                    SUNNY,
                    CLOUDY,
                    RAINY,
                    SNOW
            )
            listOf(
                    SUNNY,
                    CLOUDY,
                    RAINY,
                    SNOW,
                    HEAVY_SNOW,
                    HEAVY_RAINY
            ) + composables.flatMap { w1 ->
                composables.filter { w1 != it }.flatMap {
                    listOf(w1.sometimes(it), w1.then(it))
                }
            }
        }

        fun fromCode(code: Int): Weather {
            if (code !in 1..30) {
                throw IllegalArgumentException("code must be between 1 and 30")
            }

            return values.firstOrNull { it.code == code } ?: throw Exception("something went wrong")
        }
    }

    object HEAVY_RAINY : Weather() {
        override val code = 22
    }

    object HEAVY_SNOW : Weather() {
        override val code = 30
    }

    sealed class Composable(override val code: Int, private val codeStep: Int) : Weather() {
        infix fun sometimes(after: Composable): Weather =
                Composition.Sometimes(this, after)

        infix fun then(after: Composable): Weather =
                Composition.Then(this, after)

        private sealed class Composition(val before: Composable, val after: Composable, val step: Int, val jp: String, val connectionEmoji: String) : Weather() {
            init {
                if (before == after) {
                    throw IllegalArgumentException("before and after must not be same weather")
                }
            }

            override val code
                get() = (before.code + after.codeStep + step).let { code ->
                    if (before.code < after.code) {
                        code - 1
                    } else code
                }

            override fun toString(): String = "$before$jp$after"
            override val emoji
                get() = "${before.emoji}$connectionEmoji${after.emoji}"

            class Sometimes(before: Composable, after: Composable) : Composition(before, after, 0, "時々", "")
            class Then(before: Composable, after: Composable) : Composition(before, after, 3, "のち", ":arrow_right:")
        }

        object SUNNY : Composable(1, 1)
        object CLOUDY : Composable(8, 2)
        object RAINY : Composable(15, 3)
        object SNOW : Composable(23, 4)
    }

    abstract val code: Int
    val image by lazy {
        URL("https://static.tenki.jp/images/icon/forecast-days-weather/${String.format("%02d", this.code)}.png")
    }

    override operator fun equals(other: Any?) = (other as? Weather)?.let { this.code == it.code } ?: false

    override fun toString(): String = when (this) {
        SUNNY -> "晴れ"
        CLOUDY -> "曇り"
        RAINY -> "雨"
        SNOW -> "雪"
        HEAVY_RAINY -> "大雨"
        HEAVY_SNOW -> "大雪"
        else -> ""
    }

    open val emoji: String
        get() = when (this) {
            SUNNY -> "☀️"
            CLOUDY -> "☁️"
            RAINY -> "☂️"
            SNOW -> "⛄"
            HEAVY_RAINY -> "☔"
            HEAVY_SNOW -> "☃️"
            else -> ""
        }
}
