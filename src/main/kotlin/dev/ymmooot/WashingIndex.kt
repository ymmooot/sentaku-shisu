package dev.ymmooot

import java.net.URL

enum class WashingIndex(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    val image: URL
        get() = URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-${this.value}.png")

    override fun toString(): String =
            when (this) {
        ONE -> "部屋干し推奨"
        TWO -> "やや乾く"
        THREE -> "乾く"
        FOUR -> "よく乾く"
        FIVE -> "大変よく乾く"
    }

    companion object {
        fun fromInt(index: Int): WashingIndex =
            values().firstOrNull { it.value == index } ?: throw IllegalArgumentException("Level must be between 1 and 5")
        }
}
