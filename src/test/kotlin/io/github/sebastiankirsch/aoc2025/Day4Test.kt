package io.github.sebastiankirsch.aoc2025

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day4Test {

    @Test
    fun examplePart1() {
        val result = Day4(
            """
                    ..@@.@@@@.
                    @@@.@.@.@@
                    @@@@@.@.@@
                    @.@@@@..@.
                    @@.@@@@.@@
                    .@@@@@@@.@
                    .@.@.@.@@@
                    @.@@@.@@@@
                    .@@@@@@@@.
                    @.@.@@@.@.""".trim()
                .lines().map { it.trim().toCharArray() }.toTypedArray()
        ).accessiblePaperRolls()

        assertEquals(13, result)
    }

    @Test
    fun examplePart2() {
        val result = Day4(
            """
                    ..@@.@@@@.
                    @@@.@.@.@@
                    @@@@@.@.@@
                    @.@@@@..@.
                    @@.@@@@.@@
                    .@@@@@@@.@
                    .@.@.@.@@@
                    @.@@@.@@@@
                    .@@@@@@@@.
                    @.@.@@@.@.""".trim()
                .lines().map { it.trim().toCharArray() }.toTypedArray()
        ).removeRolls()

        assertEquals(43, result)
    }

}