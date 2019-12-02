package com.github.frozensync.day1

import java.nio.file.Files
import java.nio.file.Path

val INPUT_FILE_PATH: Path = Path.of("src/com/github/frozensync/day1/input.txt")

fun main() {
    Files.lines(INPUT_FILE_PATH)
        .mapToInt { it.toInt() }
        .map { it / 3 - 2 }
        .sum()
        .let { println(it) }
}
