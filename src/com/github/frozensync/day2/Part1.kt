package com.github.frozensync.day2

import java.io.File
import java.util.*

const val ADDITION = 1
const val MULTIPLICATION = 2
const val TERMINATION = 99

fun main() {
    val inputFile = File("src/com/github/frozensync/day2/input.txt")
    val intCodes = parseFile(inputFile)

    intCodes[1] = 12
    intCodes[2] = 2
    executeIntcodeProgram(intCodes)

    println(intCodes[0])
}

fun executeIntcodeProgram(intCodes: MutableList<Int>) {
    for (i in intCodes.indices step 4) {
        val opCode = intCodes[i]
        if (opCode == TERMINATION) return

        val position1 = intCodes[i + 1]
        val number1 = intCodes[position1]

        val position2 = intCodes[i + 2]
        val number2 = intCodes[position2]

        val position3 = intCodes[i + 3]
        when (opCode) {
            ADDITION -> intCodes[position3] = number1 + number2
            MULTIPLICATION -> intCodes[position3] = number1 * number2
        }
    }
}

fun parseFile(inputFile: File): MutableList<Int> {
    val scanner = Scanner(inputFile).useDelimiter("[,\\r?\\n]")
    val result = mutableListOf<Int>()

    while (scanner.hasNextInt()) {
        val intCode = scanner.nextInt()
        result.add(intCode)
    }

    return result
}
