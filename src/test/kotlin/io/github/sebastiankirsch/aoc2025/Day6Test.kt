package io.github.sebastiankirsch.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun examplePart1() {
        val result = Day6(
            arrayOf(
                longArrayOf(123, 328, 51, 64),
                longArrayOf(45, 64, 387, 23),
                longArrayOf(6, 98, 215, 314)
            ),
            charArrayOf('*', '+', '*', '+')
        ).grandTotal()

        assertEquals(4277556L, result)
    }

}