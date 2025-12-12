package io.github.sebastiankirsch.aoc2025

open class CharMap(val chars: Array<CharArray>) {
    override fun toString(): String {
        return chars.joinToString(separator = "\n") { it.concatToString() }
    }

    fun forEach(action: (Pair<Int, Int>, Char) -> Unit) {
        (0..<chars.size).forEach { y ->
            (0..<chars[0].size).forEach { x ->
                action(Pair(x, y), chars[y][x])
            }
        }
    }

    fun nullableCharAt(point: Pair<Int, Int>): Char? {
        if (!isWithinBounds(point))
            return null
        return chars[point.second][point.first]
    }

    fun charAt(point: Pair<Int, Int>): Char = nullableCharAt(point)
        ?: throw ArrayIndexOutOfBoundsException("Point ${point.first},${point.second} is out of bounds!")

    fun setChar(point: Pair<Int, Int>, char: Char) {
        if (!isWithinBounds(point))
            throw ArrayIndexOutOfBoundsException("Point ${point.first},${point.second} is out of bounds!")
        chars[point.second][point.first] = char
    }

    fun eightNeighborsOf(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(
            point.first + 1 to point.second + 1,
            point.first + 1 to point.second,
            point.first + 1 to point.second - 1,
            point.first to point.second + 1,
            point.first to point.second - 1,
            point.first - 1 to point.second + 1,
            point.first - 1 to point.second,
            point.first - 1 to point.second - 1,
        ).filter { isWithinBounds(it) }
    }

    fun fourNeighborsOf(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(
            point.first + 1 to point.second,
            point.first - 1 to point.second,
            point.first to point.second + 1,
            point.first to point.second - 1,
        ).filter { isWithinBounds(it) }
    }

    fun findChar(char: Char): Pair<Int, Int> {
        (0..<chars.size).forEach { y ->
            (0..<chars[0].size).forEach { x ->
                if (chars[y][x] == char)
                    return x to y
            }
        }
        throw RuntimeException()
    }

    private fun isWithinBounds(point: Pair<Int, Int>): Boolean {
        return (point.first > -1 && point.second > -1 && point.second < chars.size && point.first < chars[0].size)
    }

}