package io.github.sebastiankirsch.aoc2025

import kotlin.math.absoluteValue

fun main() {
    val input = with(scannerForInputOf(object {}.javaClass)) {
        mutableListOf<Pair<Int, Int>>().apply {
            while (hasNextLine()) {
                nextLine().split(',').map { it.toInt() }.let {
                    add(Pair(it[0], it[1]))
                }
            }
        }.toTypedArray()
    }

    val day9 = Day9(input)
    println("Area of largest rectangle: ${day9.largestRectangle()}")
    println("Area of largest red/green rectangle: ${day9.largestRedGreenRectangle()}")
}

class Day9(val redTiles: Array<Pair<Int, Int>>) {
    fun largestRectangle(): Long {
        var maxArea = 0L
        redTiles.forEachIndexed { i, firstPoint ->
            redTiles.forEachIndexed { j, secondPoint ->
                if (i > j) {
                    val area = ((firstPoint.first - secondPoint.first).absoluteValue.toLong() + 1) *
                            ((firstPoint.second - secondPoint.second).absoluteValue + 1)
                    maxArea = maxOf(area, maxArea)
                }
            }
        }

        return maxArea
    }

    fun largestRedGreenRectangle(): Long {
        val charMap = prepareCharMap()

        var maxArea = 0L
        var iteration = 0
        redTiles.forEachIndexed { i, firstPoint ->
            redTiles.forEachIndexed { j, secondPoint ->
                if (i > j) {
                    iteration++
                    val area = ((firstPoint.first - secondPoint.first).absoluteValue.toLong() + 1) *
                            ((firstPoint.second - secondPoint.second).absoluteValue + 1)
                    if (area > maxArea) {
                        println("Iteration #${iteration++}| area: $area")
                        for (x in xRange(firstPoint, secondPoint)) {
                            for (y in yRange(firstPoint, secondPoint)) {
                                if (charMap.charAt(x to y) == '.') {
                                    return@forEachIndexed
                                }
                            }
                        }
                        maxArea = area
                    }
                }
            }
        }

        return maxArea
    }

    private fun prepareCharMap(): CharMap {
        val charMap = buildCharMap()
        setRedTiles(charMap)
        setGreenTiles(charMap)
        fillWithGreenTiles(charMap)

        println("CharMap prepared")
        if (charMap.chars.size < 20) {
            println(charMap)
        }
        return charMap
    }

    private fun buildCharMap(): CharMap {
        var maxX = 0
        var minX = Int.MAX_VALUE
        var maxY = 0
        var minY = Int.MAX_VALUE
        redTiles.forEach {
            maxX = maxOf(maxX, it.first)
            minX = minOf(minX, it.first)
            maxY = maxOf(maxY, it.second)
            minY = minOf(minY, it.second)
        }

        println("Need map of $maxX x $maxY; mins are $minX x $minY")

        val charMap = CharMap(Array(maxY + 1) { _ -> CharArray(maxX + 1) { _ -> '.' } })
        return charMap
    }

    private fun setRedTiles(charMap: CharMap) {
        redTiles.forEach { charMap.setChar(it, '#') }
    }

    private fun setGreenTiles(charMap: CharMap) {
        redTiles.forEachIndexed { i, point ->
            val connectedPoint = if (i == 0) redTiles.last() else redTiles[i - 1]
            for (x in xRange(point, connectedPoint)) {
                for (y in yRange(point, connectedPoint)) {
                    if (charMap.charAt(x to y) != '#') {
                        charMap.setChar(x to y, 'X')
                    }
                }
            }
        }
    }

    private fun fillWithGreenTiles(charMap: CharMap) {
        charMap.forEach { point, char -> if (char == '.' && charMap.isInBox(point)) charMap.setChar(point, '8') }
    }

    private fun paintGreen(charMap: CharMap, pointInBox: Pair<Int, Int>) {
        val neighbors = mutableListOf(pointInBox)
        var i = 0
        do {
            val newNeighbors =
                neighbors.flatMap { point -> charMap.fourNeighborsOf(point).filter { charMap.charAt(it) == '.' } }
            newNeighbors.forEach {
                charMap.setChar(it, '0')
            }
            neighbors.clear()
            neighbors.addAll(newNeighbors)
            println("#${i++}|Neighbors: ${neighbors.size}")
        } while (newNeighbors.isNotEmpty())
    }

    private fun xRange(firstPoint: Pair<Int, Int>, secondPoint: Pair<Int, Int>): IntRange =
        minOf(firstPoint.first, secondPoint.first)..maxOf(firstPoint.first, secondPoint.first)

    private fun yRange(firstPoint: Pair<Int, Int>, secondPoint: Pair<Int, Int>): IntRange =
        minOf(firstPoint.second, secondPoint.second)..maxOf(firstPoint.second, secondPoint.second)

}

private fun CharMap.isInBox(point: Pair<Int, Int>): Boolean {
    if (!isWithinBounds(point)) {
        return false
    }
    if (charAt(point) != '.') {
        return true
    }
    val xDistanceTo0 = point.first
    val xDistanceToEdge = chars[0].size - point.first
    val yDistanceTo0 = point.second
    val yDistanceToEdge = chars.size - point.second
    val smallestDistance = minOf(xDistanceTo0, minOf(xDistanceToEdge, minOf(yDistanceTo0, yDistanceToEdge)))
    val path = mutableListOf<Char>()
    if (smallestDistance == xDistanceToEdge) {
        var newX = point.first
        while (++newX < chars[0].size) {
            path.add(charAt(newX to point.second))
        }
    } else if (smallestDistance == xDistanceTo0) {
        var newX = point.first
        while (--newX >= 0) {
            path.add(charAt(newX to point.second))
        }
    } else if (smallestDistance == yDistanceToEdge) {
        var newY = point.second
        while (++newY < chars.size) {
            path.add(charAt(point.first to newY))
        }
    } else {
        var newY = point.second
        while (--newY >= 0) {
            path.add(charAt(point.first to newY))
        }
    }
    return path.filter { it == '#' || it == 'X' }.size % 2 == 1
}
