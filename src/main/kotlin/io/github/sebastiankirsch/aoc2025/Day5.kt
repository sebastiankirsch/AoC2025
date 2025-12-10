package io.github.sebastiankirsch.aoc2025

fun main() {
    val input = with(scannerForInputOf(object {}.javaClass)) {
        val rangePattern = """\d+-\d+"""
        val ranges = mutableListOf<LongRange>().apply {
            while (hasNext(rangePattern)) {
                next(rangePattern).let {
                    val delimiter = it.indexOf('-')
                    add(it.substring(0, delimiter).toLong()..it.substring(delimiter + 1).toLong())
                }
            }
        }
        val ids = mutableListOf<Long>().apply {
            while (hasNextLong()) {
                add(nextLong())
            }
        }

        ranges to ids
    }

    println("Fresh ingredients: ${Day5(input.first, input.second).freshIngredients()}")
    println("Fresh IDs: ${Day5(input.first, input.second).freshIds()}")
}

class Day5(val idRanges: List<LongRange>, val ids: List<Long>) {
    fun freshIngredients(): Int {
        var freshIngredients = 0
        ids.forEach { id ->
            run isIdInRange@{
                idRanges.forEach { range ->
                    if (range.contains(id)) {
                        freshIngredients++
                        return@isIdInRange
                    }
                }
            }
        }
        return freshIngredients
    }

    fun freshIds(): Long {
        var ids = 0L
        val processedRanges = mutableListOf<LongRange>()
        idRanges.forEach { range ->
            var start = range.first
            var end = range.last

            val processedRangesIterator = processedRanges.iterator()
            while (processedRangesIterator.hasNext()) {
                val processedRange = processedRangesIterator.next()
                if (processedRange.contains(start)) {
                    if (processedRange.contains(end)) {
                        return@forEach
                    }
                    start = minOf(processedRange.last + 1, end)
                } else if (processedRange.contains(end)) {
                    end = maxOf(processedRange.first - 1, start)
                }
                val newRange = start..end
                if (newRange.contains(processedRange.first) && newRange.contains(processedRange.last)) {
                    processedRangesIterator.remove()
                    ids -= processedRange.last + 1 - processedRange.first
                }
            }

            ids += end + 1 - start
            processedRanges.add(range)
        }
        return ids
    }

}

