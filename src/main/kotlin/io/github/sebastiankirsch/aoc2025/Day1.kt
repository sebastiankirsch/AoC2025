package io.github.sebastiankirsch.aoc2025

fun main() {
    var position = 50
    var numberOfZerosAtEndOfRotation = 0
    var numberOfZerosDuringRotation = 0

    val scanner = scannerForInputOf(object {}.javaClass)
    while (scanner.hasNext()) {
        val oldPosition = position
        val instruction = scanner.next()
        val direction = instruction[0]
        val ticks = instruction.substring(1).toInt()


        if ('L' == direction) {
            position -= ticks
        } else {
            position += ticks
        }

        while (position > 100) {
            position -= 100
            numberOfZerosDuringRotation++
        }
        if (oldPosition == 0 && position < 0) {
            position += 100
        }
        while (position < 0) {
            position += 100
            numberOfZerosDuringRotation++
        }

        position %= 100

        if (position == 0) {
            numberOfZerosAtEndOfRotation++
        }
    }

    println("Simple Password: $numberOfZerosAtEndOfRotation")
    println("0x434C49434B Password: ${numberOfZerosAtEndOfRotation + numberOfZerosDuringRotation}")
}
