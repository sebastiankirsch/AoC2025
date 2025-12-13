package io.github.sebastiankirsch.aoc2025

import java.util.Scanner

fun main() {
    val input = with(scannerForInputOf(object {}.javaClass)) {
        mutableListOf<Triple<BooleanArray, Array<IntArray>, IntArray>>().apply {
            while (hasNextLine()) {
                with(Scanner(nextLine())) {
                    val indicatorLights = next("""\[[.#]+]""").let { it.substring(1, it.length - 1) }
                        .map { it == '#' }.toBooleanArray()
                    val buttons = mutableListOf<IntArray>().apply {
                        while (hasNext("""\((\d+,?)+\)""")) {
                            add(next("""\((\d+,?)+\)""").let {
                                it.substring(1, it.length - 1).split(",").map { it.toInt() }.toIntArray()
                            })
                        }
                    }.toTypedArray()
                    val joltage = next("""\{(\d+,?)+}""").let {
                        it.substring(1, it.length - 1).split(",").map { it.toInt() }.toIntArray()
                    }
                    add(Triple(indicatorLights, buttons, joltage))
                }
            }
        }.toTypedArray()
    }

    val day10 = Day10(input)
    println("Fewest buttons to press: ${day10.fewestButtonPresses()}")
}

class Day10(input: Array<Triple<BooleanArray, Array<IntArray>, IntArray>>) {
    val indicators = input.map {
        var value = 0
        it.first.forEachIndexed { i, indicator ->
            if (indicator) {
                value += (1 shl i)
            }
        }
        value
    }.toIntArray()

    val buttons = input.map {
        it.second.map { buttons ->
            var value = 0
            buttons.forEach { value += 1 shl it }
            value
        }.toIntArray()
    }.toTypedArray()

    fun fewestButtonPresses(): Int {
        printState()

        TODO()
    }

    private fun printState() {
        indicators.forEachIndexed { i, indicator ->
            print('[')
            var value = indicator
            while (value > 0) {
                print(if ((value and 1) == 1) '#' else '.')
                value = value shr 1
            }
            print(']')
            buttons[i].forEach {
                print(" (")
                var needsComma = false
                for (i in 0..16) {
                    val bitMask = 1 shl i
                    if ((bitMask and it) == bitMask) {
                        if (needsComma) print(',')
                        print(i)
                        needsComma = true
                    }
                }
                print(')')
            }
            println()
        }
    }

}