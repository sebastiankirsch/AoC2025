package io.github.sebastiankirsch.aoc2025

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day10Test {
    val testSubject = Day10(
        arrayOf(
            //[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            Triple(
                booleanArrayOf(false, true, true, false),
                arrayOf(
                    intArrayOf(3),
                    intArrayOf(1, 3),
                    intArrayOf(2),
                    intArrayOf(2, 3),
                    intArrayOf(0, 2),
                    intArrayOf(0, 1),
                ),
                intArrayOf(3, 4, 5, 7)
            ),
            //[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            Triple(
                booleanArrayOf(false, false, false, true, false),
                arrayOf(
                    intArrayOf(0, 2, 3, 4),
                    intArrayOf(2, 3),
                    intArrayOf(0, 4),
                    intArrayOf(0, 1, 2),
                    intArrayOf(1, 2, 3, 4),
                ),
                intArrayOf(7, 5, 12, 7, 2)
            ),
            //[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            Triple(
                booleanArrayOf(false, true, true, true, false, true),
                arrayOf(
                    intArrayOf(0, 1, 2, 3, 4),
                    intArrayOf(0, 3, 4),
                    intArrayOf(0, 1, 2, 4, 5),
                    intArrayOf(1, 2),
                ),
                intArrayOf(10, 11, 11, 5, 10, 5)
            ),
        )
    )

    @Test
    fun `example of part I`() {
        val result = testSubject.fewestButtonPresses()

        assertEquals(7, result)
    }

}