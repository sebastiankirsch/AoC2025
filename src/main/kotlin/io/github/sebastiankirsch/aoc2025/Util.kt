package io.github.sebastiankirsch.aoc2025

import java.util.Scanner

fun scannerForInputOf(clazz: Class<*>): Scanner {
    val className = clazz.name.substring(clazz.packageName.length + 1, clazz.name.indexOf("Kt$"))
    return Scanner(clazz.getResource("/${className}.txt")!!.openStream())
}

fun linesFromInputOf(clazz: Class<*>): List<String> {
    val scanner = scannerForInputOf(clazz)
    val lines = mutableListOf<String>()
    while (scanner.hasNextLine()) lines.add(scanner.nextLine())
    return lines
}

fun charsArrayFromInputOf(clazz: Class<*>): Array<CharArray> {
    return linesFromInputOf(clazz).map { it.toCharArray() }.toTypedArray()
}

fun intArraysFromInputOf(clazz: Class<*>): Array<IntArray> {
    return linesFromInputOf(clazz).map {
        it.toCharArray().map { c -> String(CharArray(1) { _ -> c }).toInt() }.toIntArray()
    }.toTypedArray()
}

fun charAt(chars: Array<CharArray>, x: Int, y: Int): Char? {
    if (x < 0 || y < 0 || y >= chars.size || x >= chars[0].size)
        return null
    return chars[y][x]
}