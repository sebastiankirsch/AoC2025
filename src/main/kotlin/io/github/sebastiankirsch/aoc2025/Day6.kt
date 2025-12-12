package io.github.sebastiankirsch.aoc2025

import java.util.Scanner

fun main() {
    val humanInput = with(scannerForInputOf(object {}.javaClass)) {
        val numbers = mutableListOf<List<Long>>().apply {
            while (hasNextLong()) {
                with(Scanner(nextLine())) {
                    val row = mutableListOf<Long>().apply {
                        while (hasNextLong()) add(nextLong())
                    }
                    add(row)
                }
            }
        }
        val operations = mutableListOf<Char>().apply {
            while (hasNext()) add(next()[0])
        }

        val transformedNumbers = Array(operations.size) { i ->
            LongArray(numbers.size) { j -> numbers[j][i] }
        }

        transformedNumbers to operations.toCharArray()
    }

    val cephalopodInput = with(charsArrayFromInputOf(object {}.javaClass)) {
        val operations = mutableListOf<Char>()
        val numbers = mutableListOf<LongArray>()
        val innerNumbers = mutableListOf<Long>()
        for (i in this[0].size - 1 downTo 0) {
            val numberString = CharArray(this.size - 1) { j ->
                (this[j][i])
            }.joinToString("").trim()
            if (numberString.isBlank()) {
                continue
            }
            val number = numberString.toLong()
            innerNumbers.add(number)
            if (this[this.size - 1][i] != ' ') {
                operations.add(this[this.size - 1][i])
                numbers.add(innerNumbers.toLongArray())
                innerNumbers.clear()
            }
        }

        numbers.toTypedArray() to operations.toCharArray()
    }

    println("Human grand total: ${Day6(humanInput.first, humanInput.second).grandTotal()}")
    println("Cephalopod grand total: ${Day6(cephalopodInput.first, cephalopodInput.second).grandTotal()}")
}

class Day6(val numbers: Array<LongArray>, val operations: CharArray) {
    fun grandTotal(): Long {
        var grandTotal = 0L

        operations.forEachIndexed { i, operation ->
            var result = if ('+' == operation) 0L else 1L
            numbers[i].forEach{  number ->
                when (operation) {
                    '+' -> result += number
                    '*' -> result *= number
                    else -> throw RuntimeException("This is unexpected: $operation")
                }
            }
            grandTotal += result
        }

        return grandTotal
    }
}

