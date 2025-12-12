package io.github.sebastiankirsch.aoc2025

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day9Test {

    val testSubject =
        Day9(
            arrayOf(
                Pair(7, 1),
                Pair(11, 1),
                Pair(11, 7),
                Pair(9, 7),
                Pair(9, 5),
                Pair(2, 5),
                Pair(2, 3),
                Pair(7, 3)
            )
        )

    @Test
    fun `example of part I`() {
        val result = testSubject.largestRectangle()

        assertEquals(50, result)
    }

    @Test
    fun `example of part II`() {
        val result = testSubject.largestRedGreenRectangle()

        assertEquals(24, result)
    }

}