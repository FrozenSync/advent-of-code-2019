package com.github.frozensync.day3

import java.io.File
import java.util.*
import kotlin.math.absoluteValue

fun main() {
    val inputFile = File("src/com/github/frozensync/day3/input.txt")
    val (instructionsForWire1, instructionsForWire2) = parseInput(inputFile)

    solvePart1(instructionsForWire1, instructionsForWire2)
    solvePart2(instructionsForWire1, instructionsForWire2)
}

private fun parseInput(inputFile: File): Pair<Instructions, Instructions> {
    Scanner(inputFile).use {
        val result1 = parseInstructions(it.nextLine())
        val result2 = parseInstructions(it.nextLine())

        return Pair(result1, result2)
    }
}

private fun parseInstructions(inputLine: String): Instructions {
    Scanner(inputLine).useDelimiter(",").use {
        val result = mutableListOf<Instruction>()

        while (it.hasNext()) {
            val instruction = parseInstruction(it.next())
            result.add(instruction)
        }

        return Instructions(result)
    }
}

private fun parseInstruction(inputToken: String): Instruction {
    Scanner(inputToken).use {
        val direction = when (val directionInput = it.useDelimiter("").next()) {
            "U" -> Direction.UP
            "R" -> Direction.RIGHT
            "D" -> Direction.DOWN
            "L" -> Direction.LEFT
            else -> throw IllegalArgumentException("Invalid direction: $directionInput")
        }
        val steps = it.useDelimiter(" ").nextInt()

        return Instruction(direction, steps)
    }
}

private fun solvePart1(instructionsForWire1: Instructions, instructionsForWire2: Instructions) {
    val pathOfWire1 = instructionsForWire1.toPath()
    val pathOfWire2 = instructionsForWire2.toPath()

    val result = pathOfWire1.intersect(pathOfWire2)
        .map { it.x.absoluteValue + it.y.absoluteValue }
        .min()

    println(result)
}

private fun solvePart2(instructionsForWire1: Instructions, instructionsForWire2: Instructions) {
    val pathOfWire1 = instructionsForWire1.toPath()
    val pathOfWire2 = instructionsForWire2.toPath()

    val result = pathOfWire1.intersect(pathOfWire2)
        .map { pathOfWire1.indexOf(it) + pathOfWire2.indexOf(it) + 2 }
        .min()

    println(result)
}
