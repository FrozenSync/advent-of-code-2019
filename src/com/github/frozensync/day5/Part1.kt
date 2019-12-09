package com.github.frozensync.day5

import java.nio.file.Files
import java.nio.file.Path

val PATH_TO_INPUT_FILE: Path = Path.of("src/com/github/frozensync/day5/input.txt")

fun main() {
    val input = parseFile(PATH_TO_INPUT_FILE)
    solvePart1(input)
}

fun parseFile(pathToFile: Path): IntArray =
    Files.lines(pathToFile)
        .flatMap { it.split(",").stream() }
        .mapToInt { it.toInt() }
        .toArray()

fun solvePart1(input: IntArray) {
    IntcodeComputer.execute(input)
}
