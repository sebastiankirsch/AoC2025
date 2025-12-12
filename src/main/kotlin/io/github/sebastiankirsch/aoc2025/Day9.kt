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
        redTiles.forEachIndexed { i, firstPoint ->
            redTiles.forEachIndexed { j, secondPoint ->
                if (i > j) {
                    val area = ((firstPoint.first - secondPoint.first).absoluteValue.toLong() + 1) *
                            ((firstPoint.second - secondPoint.second).absoluteValue + 1)
                    if (area > maxArea) {
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

        println(charMap)
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
        val pointInBox = listOf(
            redTiles[0].first + 1 to redTiles[0].second + 1,
            redTiles[0].first + 1 to redTiles[0].second - 1,
            redTiles[0].first - 1 to redTiles[0].second - 1,
            redTiles[0].first - 1 to redTiles[0].second + 1,
        ).first { charMap.isInBox(it) }
        println("Point in box: $pointInBox")
        paintGreen(charMap, pointInBox)
    }

    private fun paintGreen(charMap: CharMap, pointInBox: Pair<Int, Int>) {
        charMap.fourNeighborsOf(pointInBox).filter { charMap.charAt(it) == '.' }.forEach {
            charMap.setChar(it, 'X')
            paintGreen(charMap, it)
        }
    }

    private fun xRange(firstPoint: Pair<Int, Int>, secondPoint: Pair<Int, Int>): IntRange =
        minOf(firstPoint.first, secondPoint.first)..maxOf(firstPoint.first, secondPoint.first)

    private fun yRange(firstPoint: Pair<Int, Int>, secondPoint: Pair<Int, Int>): IntRange =
        minOf(firstPoint.second, secondPoint.second)..maxOf(firstPoint.second, secondPoint.second)

}

private fun CharMap.isInBox(point: Pair<Int, Int>): Boolean {
    val chars = mutableListOf<Char>()
    var newY = point.second
    do {
        chars.add(charAt(point.first to newY))
    } while (--newY > 0)
    return chars.filter { it != '.' }.size % 2 == 1
}
