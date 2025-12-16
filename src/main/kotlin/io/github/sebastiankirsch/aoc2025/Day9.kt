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
                    val area = area(firstPoint, secondPoint)
                    maxArea = maxOf(area, maxArea)
                }
            }
        }

        return maxArea
    }

    fun largestRedGreenRectangle(): Long {
        val bitMap = prepareBitMap()

        val maxArea = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>().apply { // list all rectangles
            redTiles.forEachIndexed { i, firstPoint ->
                redTiles.forEachIndexed { j, secondPoint ->
                    if (i > j) {
                        add(firstPoint to secondPoint)
                    }
                }
            }
        }.sortedByDescending { // sort by area
            area(it.first, it.second)
        }.filter {
            val xRange = xRange(it.first, it.second)
            val yRange = yRange(it.first, it.second)
            for (x in xRange) {
                if (!bitMap[yRange.first][x] || !bitMap[yRange.last][x]) {
                    return@filter false
                }
            }
            for (y in yRange) {
                if (!bitMap[y][xRange.first] || !bitMap[y][xRange.last]) {
                    return@filter false
                }
            }
            true
        }.map { area(it.first, it.second) }.first()

        return maxArea
    }

    private fun area(first: Pair<Int, Int>, second: Pair<Int, Int>): Long =
        ((first.first - second.first).absoluteValue.toLong() + 1) * ((first.second - second.second).absoluteValue + 1)

    private fun prepareBitMap(): Array<BooleanArray> {
        val bitMap = buildBitMap()
        drawTiles(bitMap)
        fillWithTiles(bitMap)

        if (bitMap.size < 20) {
            println(bitMap.joinToString(separator = "\n") { it.joinToString(separator = "") { if (it) "X" else "." } })
        }
        return bitMap
    }

    private fun buildBitMap(): Array<BooleanArray> {
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

        return Array(maxY + 1) { _ -> BooleanArray(maxX + 1) { _ -> false } }
    }

    private fun drawTiles(bitMap: Array<BooleanArray>) {
        redTiles.forEachIndexed { i, point ->
            val connectedPoint = if (i == 0) redTiles.last() else redTiles[i - 1]
            for (x in xRange(point, connectedPoint)) {
                for (y in yRange(point, connectedPoint)) {
                    bitMap[y][x] = true
                }
            }
        }
    }

    private fun fillWithTiles(bitMap: Array<BooleanArray>) {
        bitMap.forEachIndexed { y, row ->
            var inside = false
            var onLine = false
            var previousValue = false
            row.forEachIndexed { x, currentValue ->
                if (!currentValue) {
                    if (onLine) {
                        // upper neighbor dictates if we're in- or outside
                        inside = bitMap[y - 1][x]
                        onLine = false
                    }
                    if (inside) {
                        bitMap[y][x] = true
                    }
                } else {
                    if (previousValue) {
                        onLine = true
                    } else {
                        inside = !inside
                    }
                }
                previousValue = currentValue
            }
        }
    }

    private fun xRange(firstPoint: Pair<Int, Int>, secondPoint: Pair<Int, Int>): IntRange =
        minOf(firstPoint.first, secondPoint.first)..maxOf(firstPoint.first, secondPoint.first)

    private fun yRange(firstPoint: Pair<Int, Int>, secondPoint: Pair<Int, Int>): IntRange =
        minOf(firstPoint.second, secondPoint.second)..maxOf(firstPoint.second, secondPoint.second)

}
