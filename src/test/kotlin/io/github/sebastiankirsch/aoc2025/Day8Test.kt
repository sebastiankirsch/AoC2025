package io.github.sebastiankirsch.aoc2025

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day8Test {

    val testSubject = Day8(
        arrayOf(
            Triple(162, 817, 812),
            Triple(57, 618, 57),
            Triple(906, 360, 560),
            Triple(592, 479, 940),
            Triple(352, 342, 300),
            Triple(466, 668, 158),
            Triple(542, 29, 236),
            Triple(431, 825, 988),
            Triple(739, 650, 466),
            Triple(52, 470, 668),
            Triple(216, 146, 977),
            Triple(819, 987, 18),
            Triple(117, 168, 530),
            Triple(805, 96, 715),
            Triple(346, 949, 466),
            Triple(970, 615, 88),
            Triple(941, 993, 340),
            Triple(862, 61, 35),
            Triple(984, 92, 344),
            Triple(425, 690, 689)
        )
    )

    @Test
    fun `example of part I`() {
        val result = testSubject.threeLargestCircuits(10)

        assertEquals(40, result)
    }

    @Test
    fun `example of part II`() {
        val result = testSubject.oneLargeCircuit()

        assertEquals(25272, result)
    }

}