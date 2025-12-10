package io.github.sebastiankirsch.aoc2025

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day5Test {

    @Test
    fun examplePart1() {
        val result = Day5(
            listOf(
                3L..5,
                10L..14,
                16L..20,
                12L..18
            ),
            listOf(
                1,
                5,
                8,
                11,
                17,
                32
            )
        ).freshIngredients()

        assertEquals(3, result)
    }

    @Test
    fun examplePart2() {
        val result = Day5(
            listOf(
                3L..5,
                10L..14,
                16L..20,
                12L..18
            ),
            listOf()
        ).freshIds()

        assertEquals(14, result)
    }

    @Test
    fun part2EnclosingRange() {
        val result = Day5(
            listOf(
                1L..3,
                5L..6,
                8L..10,
                2L..9
            ),
            listOf()
        ).freshIds()

        assertEquals(10, result)
    }

}