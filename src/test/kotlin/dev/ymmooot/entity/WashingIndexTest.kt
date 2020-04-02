package dev.ymmooot.entity

import java.net.URL
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WashingIndexTest {

    @Test
    fun testToString() {
        assertEquals("部屋干し推奨", WashingIndex.ONE.toString())
        assertEquals("やや乾く", WashingIndex.TWO.toString())
        assertEquals("乾く", WashingIndex.THREE.toString())
        assertEquals("よく乾く", WashingIndex.FOUR.toString())
        assertEquals("大変よく乾く", WashingIndex.FIVE.toString())
    }

    @Test
    fun testFromInt() {
        assertEquals(WashingIndex.ONE, WashingIndex.fromInt(1))
        assertEquals(WashingIndex.TWO, WashingIndex.fromInt(2))
    }

    @Test
    fun `fromInt throws an error when argument is out of range`() {
        val exception = assertThrows<IllegalArgumentException> ("Should throw an exception") {
            WashingIndex.fromInt(6)
        }
        assertEquals("Level must be between 1 and 5", exception.message)
    }

    @Test
    fun testImage() {
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-1.png"), WashingIndex.ONE.image)
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-2.png"), WashingIndex.TWO.image)
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-3.png"), WashingIndex.THREE.image)
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-4.png"), WashingIndex.FOUR.image)
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-5.png"), WashingIndex.FIVE.image)
    }
}
