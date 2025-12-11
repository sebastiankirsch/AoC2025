package io.github.sebastiankirsch.aoc2025

import java.util.Scanner

fun main() {
    val input = with(scannerForInputOf(object {}.javaClass)) {
        val numbers = mutableListOf<List<Int>>().apply {
            while (hasNextInt()) {
                with(Scanner(nextLine())) {
                    val row = mutableListOf<Int>().apply {
                        while (hasNextInt()) {
                            add(nextInt())
                        }
                    }
                    add(row)
                }
            }
        }
        val operations = mutableListOf<Char>().apply {
            while (hasNext()) {
                add(next()[0])
            }
        }

        numbers to operations
    }

    val day6 = Day6(input.first, input.second)
    println("Grand total: ${day6.grandTotal()}")
}

class Day6(val numbers: List<List<Int>>, val operations: List<Char>) {
    fun grandTotal(): Long {
        var grandTotal = 0L

        operations.forEachIndexed { column, operation ->
            var result = numbers[0][column].toLong()
            for (row in 1..<numbers.size) {
                if ('+' == operation) {
                    result += numbers[row][column]
                } else if ('*' == operation) {
                    result *= numbers[row][column]
                } else {
                    TODO("This is unexpected: $operation")
                }
            }
            grandTotal += result
        }

        return grandTotal
    }
}

