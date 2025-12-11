package io.github.sebastiankirsch.aoc2025

fun main() {
    val charMap = charsArrayFromInputOf(object {}.javaClass)

    val day7 = Day7(charMap)
    println("The tachyon beam is split ${day7.beamSplits()} times.")
    println("The tachyon beam ends up in ${day7.timeLines()} timelines.")
}

class Day7(chars: Array<CharArray>) : CharMap(chars) {
    val initialBeam = findChar('S')

    fun beamSplits(): Int {
        return beamSplitInternal(setOf(initialBeam))
    }

    private fun beamSplitInternal(beams: Set<Pair<Int, Int>>): Int {
        val newBeams = mutableSetOf<Pair<Int, Int>>()
        var splits = 0
        beams.forEach { beam ->
            val down = beam.down()
            val charBelow = nullableCharAt(down)
            when (charBelow) {
                null -> return 0
                '^' -> {
                    splits++
                    newBeams.add(beam.downAndLeft())
                    newBeams.add(beam.downAndRight())
                }

                else -> newBeams.add(down)
            }
        }
        return splits + beamSplitInternal(newBeams)
    }

    fun timeLines(): Long {
        return timeLinesInternal(mapOf(initialBeam to 1))
    }

    private fun timeLinesInternal(beams: Map<Pair<Int, Int>, Long>): Long {
        val newBeams = mutableMapOf<Pair<Int, Int>, Long>()
        beams.forEach { beam ->
            val down = beam.key.down()
            val charBelow = nullableCharAt(down)
            when (charBelow) {
                null -> return beams.values.sum()
                '^' -> {
                    addBeams(newBeams, beam.key.downAndLeft(), beam.value)
                    addBeams(newBeams, beam.key.downAndRight(), beam.value)
                }
                else -> addBeams(newBeams, down, beam.value)
            }
        }
        return timeLinesInternal(newBeams)
    }

    private fun addBeams(
        beams: MutableMap<Pair<Int, Int>, Long>,
        beamPosition: Pair<Int, Int>,
        numberOfBeams: Long
    ) {
        beams.compute(beamPosition, { _, count -> numberOfBeams + (count ?: 0) })
    }

    private fun Pair<Int, Int>.down(): Pair<Int, Int> {
        return first to second + 1
    }

    private fun Pair<Int, Int>.downAndLeft(): Pair<Int, Int> {
        return first - 1 to second + 1
    }

    private fun Pair<Int, Int>.downAndRight(): Pair<Int, Int> {
        return first + 1 to second + 1
    }

}