package dev.ymmooot

import java.net.URL

sealed class Weather {
    private interface Composable {
        val code: Int
        val codeStep: Int
        infix fun sometimes(after: Composable): Weather = Composition.Sometimes(this, after)
        infix fun then(after: Composable): Weather = Composition.Then(this, after)
    }

    private sealed class Composition(val before: Composable, val after: Composable, private val step: Int) : Weather() {
        init {
            if (before == after) {
                throw IllegalArgumentException("before and after must not be same weather")
            }
        }

        override val code
            get() = (before.code + after.codeStep + step).let { code ->
                if (before.code < after.code) { code - 1 } else code
            }
        class Sometimes(before: Composable, after: Composable) : Composition(before, after, 0)
        class Then(before: Composable, after: Composable) : Composition(before, after, 3)
    }

    abstract val code: Int
    val image by lazy {
        URL("https://static.tenki.jp/images/icon/forecast-days-weather/${String.format("%02d", this.code)}.png")
    }

    object SUNNY : Weather(), Composable {
        override val code = 1
        override val codeStep = 1
    }
    object CLOUDY : Weather(), Composable {
        override val code = 8
        override val codeStep = 2
    }
    object RAINY : Weather(), Composable {
        override val code = 15
        override val codeStep = 3
    }
    object SNOW : Weather(), Composable {
        override val code = 23
        override val codeStep = 4
    }
    object HEAVY_RAINY : Weather() { override val code = 22 }
    object HEAVY_SNOW : Weather() { override val code = 30 }
}
