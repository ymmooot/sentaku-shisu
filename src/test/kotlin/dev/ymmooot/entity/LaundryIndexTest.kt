package dev.ymmooot.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.URL

class LaundryIndexTest {

    @Test
    fun testToString() {
        assertEquals("部屋干し推奨", LaundryIndex.ONE.toString())
        assertEquals("やや乾く", LaundryIndex.TWO.toString())
        assertEquals("乾く", LaundryIndex.THREE.toString())
        assertEquals("よく乾く", LaundryIndex.FOUR.toString())
        assertEquals("大変よく乾く", LaundryIndex.FIVE.toString())
    }

    @Test
    fun testFromInt() {
        assertEquals(LaundryIndex.ONE, LaundryIndex.fromInt(1))
        assertEquals(LaundryIndex.TWO, LaundryIndex.fromInt(2))
    }

    @Test
    fun `fromInt throws an error when argument is out of range`() {
        val exception = assertThrows<IllegalArgumentException>("Should throw an exception") {
            LaundryIndex.fromInt(6)
        }
        assertEquals("Level must be between 1 and 5", exception.message)
    }

    @Test
    fun testImage() {
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-1.png"), LaundryIndex.ONE.image)
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-2.png"), LaundryIndex.TWO.image)
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-3.png"), LaundryIndex.THREE.image)
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-4.png"), LaundryIndex.FOUR.image)
        assertEquals(URL("https://static.tenki.jp/images/icon/indexes/cloth_dried/icon-large-5.png"), LaundryIndex.FIVE.image)
    }
}
