package io.github.sebastiankirsch.aoc2025

import kotlin.math.pow

fun main() {
    val batteries = intArraysFromInputOf(object {}.javaClass)

    println("Total output joltage: ${Day3(batteries).totalJoltage(2)}")
    println("Security override joltage: ${Day3(batteries).totalJoltage(12)}")
}

class Day3(val batteries: Array<IntArray>) {
    fun totalJoltage(numberOfBatteries: Int): Long {
        var totalJoltage = 0L
        batteries.forEach { batteryBank ->
            var bestCombo = batteryBank.slice(0..<numberOfBatteries)
            batteryBank.drop(numberOfBatteries).forEach { nextBattery ->
                bestCombo = calculatePossibleCombos(bestCombo, nextBattery)
                    .plus(bestCombo)
                    .maxBy { it.joltage() }
            }
            totalJoltage += bestCombo.joltage()
        }

        return totalJoltage
    }

    private fun calculatePossibleCombos(currentCombo: List<Int>, nextBattery: Int): Array<List<Int>> {
        return Array(currentCombo.size) { i ->
            currentCombo.dropElementAt(i).plus(nextBattery)
        }
    }

    private fun <E> List<E>.dropElementAt(index: Int): MutableList<E> {
        val copy = toMutableList()
        copy.removeAt(index)
        return copy
    }

    private fun List<Int>.joltage(): Long {
        var joltage = 0L
        forEachIndexed { index, value -> joltage += 10.0.pow(size - 1 - index).toLong() * value }
        return joltage
    }

}

