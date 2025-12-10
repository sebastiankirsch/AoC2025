package io.github.sebastiankirsch.aoc2025

open class CharMap(val chars: Array<CharArray>) {

    fun forEach(action: (Pair<Int, Int>, Char) -> Unit) {
        (0..chars.size - 1).forEach { y ->
            (0..chars[0].size - 1).forEach { x ->
                action(Pair(x, y), chars[y][x])
            }
        }
    }

    fun charAt(point: Pair<Int, Int>): Char {
        if (!isWithinBounds(point))
            throw ArrayIndexOutOfBoundsException("Point ${point.first},${point.second} is out of bounds!")
        return chars[point.second][point.first]
    }

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

    private fun isWithinBounds(point: Pair<Int, Int>): Boolean {
        return (point.first > -1 && point.second > -1 && point.second < chars.size && point.first < chars[0].size)
    }

}