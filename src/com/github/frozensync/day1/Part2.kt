package com.github.frozensync.day1

import java.nio.file.Files

fun main() {
    Files.lines(INPUT_FILE_PATH)
        .mapToInt { it.toInt() }
        .map { calculateFuel(it) }
        .sum()
        .let { println(it) }
}

private fun calculateFuel(mass: Int): Int {
    val result = mass / 3 - 2
    if (result <= 0) return 0

    return result + calculateFuel(result)
}