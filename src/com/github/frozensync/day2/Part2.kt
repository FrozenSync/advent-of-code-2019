package com.github.frozensync.day2

import java.io.File

fun main() {
    val inputFile = File("src/com/github/frozensync/day2/input.txt")
    val intCodes = parseFile(inputFile)

    for (noun in 0..99) {
        for (verb in 0..99) {
            val memory = intCodes.toMutableList()
            memory[1] = noun
            memory[2] = verb
            executeIntcodeProgram(memory)

            if (memory[0] == 19690720) {
                val result = 100 * noun + verb
                println("$result")
            }
        }
    }
}
