package io.github.sebastiankirsch.aoc2025

import kotlin.math.log10
import kotlin.math.pow

fun main() {
    val idRanges = with(scannerForInputOf(object {}.javaClass).useDelimiter(",")) {
        val ranges = mutableListOf<Pair<Long, Long>>()
        while (hasNext()) {
            val pattern = next()
            val delimiter = pattern.indexOf('-')
            ranges.add(Pair(pattern.substring(0, delimiter).toLong(), pattern.substring(delimiter + 1).toLong()))
        }
        ranges
    }

    println("Simple invalid IDs: ${Day2(idRanges).simpleInvalidIds()}")
    println("Complex invalid IDs: ${Day2(idRanges).invalidIds()}")
}

class Day2(val idRanges: List<Pair<Long, Long>>) {
    fun simpleInvalidIds(): Long {
        var invalidIds = 0L
        idRanges.forEach {
            for (id in it.first..it.second) {
                val numberOfDigits = (log10(id.toDouble()) + 1).toInt()
                if (numberOfDigits % 2 != 0) {
                    continue
                }
                val halfwayPoint = 10.0.pow((numberOfDigits / 2).toDouble()).toInt()

                if (id / halfwayPoint == id % halfwayPoint) {
                    invalidIds += id
                }
            }
        }
        return invalidIds
    }

    fun invalidIds(): Long {
        var invalidIds = 0L
        idRanges.forEach {
            ids@ for (id in it.first..it.second) {
                if (id < 10) {
                    continue // single-digits are valid IDs
                }
                val idAsString = id.toString()
                val numberOfDigits = idAsString.length
                patterns@ for (patternSize in 1..numberOfDigits / 2) {
                    if (numberOfDigits % patternSize != 0) {
                        continue // pattern doesn't "fit" into ID
                    }
                    val pattern = idAsString.take(patternSize)
                    for (i in 1..<(numberOfDigits / patternSize)) {
                        if (pattern != idAsString.substring(i * patternSize, (i + 1) * patternSize)) {
                            continue@patterns
                        }
                    }
                    invalidIds += id
                    continue@ids
                }
            }
        }
        return invalidIds
    }
}
