package io.github.sebastiankirsch.aoc2025

fun main() {
    val charMap = charsArrayFromInputOf(object {}.javaClass)

    println("Initially accessible paper rolls: ${Day4(charMap).accessiblePaperRolls()}")
    println("Removed paper rolls: ${Day4(charMap).removeRolls()}")
}

class Day4(chars: Array<CharArray>) : CharMap(chars) {
    fun accessiblePaperRolls(): Int {
        var accessiblePaperRolls = 0
        forEach { point, char ->
            if ('@' != char) {
                return@forEach
            }
            if (eightNeighborsOf(point).filter { charAt(it) == '@' }.size < 4) {
                accessiblePaperRolls++
            }
        }
        return accessiblePaperRolls
    }

    fun removeRolls(): Int {
        var totalRemovedRolls = 0
        do {
            val removedRolls = removeRollsInternal()
            totalRemovedRolls += removedRolls
        } while (removedRolls > 0)
        return totalRemovedRolls
    }

    private fun removeRollsInternal(): Int {
        var removedRolls = 0
        forEach { point, char ->
            if ('@' != char) {
                return@forEach
            }
            if (eightNeighborsOf(point).filter { charAt(it) == '@' }.size < 4) {
                setChar(point, 'x')
                removedRolls++
            }
        }
        return removedRolls
    }

}

