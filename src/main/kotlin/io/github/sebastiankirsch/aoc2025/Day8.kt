package io.github.sebastiankirsch.aoc2025

import java.util.function.BiFunction
import java.util.function.Function
import kotlin.math.pow

fun main() {
    val input = with(scannerForInputOf(object {}.javaClass)) {
        mutableListOf<Triple<Int, Int, Int>>().apply {
            while (hasNextLine()) {
                nextLine().split(',').map { it.toInt() }.let {
                    add(Triple(it[0], it[1], it[2]))
                }
            }
        }.toTypedArray()
    }

    val day8 = Day8(input)
    println("Product of three largest circuits: ${day8.threeLargestCircuits(1000)}")
    println("One circuit: ${day8.oneLargeCircuit()}")
}

class Day8(val circuitBoxes: Array<Triple<Int, Int, Int>>) {

    fun threeLargestCircuits(connections: Int): Int {
        return connectJunctions(
            connectionsToExamine = connections,
            intermediateFunction = { _, _ -> null }, // loop though all junction pairs
            resultFunction = { circuits -> circuits.map { it.size }.sortedDescending().take(3).reduce(Int::times) }
        )
    }

    fun oneLargeCircuit(): Long {
        return connectJunctions(
            intermediateFunction = { circuits, pair ->
                if (circuits.size == 1 && circuits[0].size == circuitBoxes.size)  // one circuit to rule them all
                    pair.first.first.toLong() * pair.second.first else null
            },
            resultFunction = { _ -> throw RuntimeException("unreachable") }
        )
    }

    private fun <R> connectJunctions(
        connectionsToExamine: Int? = null,
        intermediateFunction: BiFunction<List<Set<Triple<Int, Int, Int>>>, Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>, R?>,
        resultFunction: Function<List<Set<Triple<Int, Int, Int>>>, R>
    ): R {
        val circuits = mutableListOf<MutableSet<Triple<Int, Int, Int>>>()
        findClosestPairs().let { it.take(connectionsToExamine ?: it.size) }.forEach {
            val firstCircuit = circuits.indexOfCircuitContaining(it.first)
            val secondCircuit = circuits.indexOfCircuitContaining(it.second)
            if (firstCircuit != null) {
                if (secondCircuit != null) {
                    if (firstCircuit != secondCircuit) { // merge circuits
                        circuits[firstCircuit].addAll(circuits.removeAt(secondCircuit))
                    } // else both points are in same circuit already
                } else { // second box is no circuit, add to first
                    circuits[firstCircuit].add(it.second)
                }
            } else if (secondCircuit != null) { // first box is no circuit, add to second
                circuits[secondCircuit].add(it.first)
            } else { // no circuit etablished
                circuits.add(mutableSetOf(it.first, it.second))
            }
            // return intermediate result if it exists
            intermediateFunction.apply(circuits, it)?.let { result -> return result }
        }
        return resultFunction.apply(circuits)
    }

    private fun findClosestPairs(): List<Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>> {
        val distances = mutableMapOf<Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>, Double>()
        circuitBoxes.forEachIndexed { i, firstPoint ->
            circuitBoxes.forEachIndexed { j, secondPoint ->
                if (i > j) {
                    val distance = (firstPoint.first - secondPoint.first).toDouble().pow(2) +
                            (firstPoint.second - secondPoint.second).toDouble().pow(2) +
                            (firstPoint.third - secondPoint.third).toDouble().pow(2)
                    distances[firstPoint to secondPoint] = distance
                }
            }
        }

        return distances.toList().sortedBy { (_, value) -> value }.map { it.first }
    }

    private fun MutableList<MutableSet<Triple<Int, Int, Int>>>.indexOfCircuitContaining(point: Triple<Int, Int, Int>): Int? {
        forEachIndexed { i, circuit -> if (circuit.contains(point)) return i }
        return null
    }

}
