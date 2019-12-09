package com.github.frozensync.day5

import java.util.*

enum class Opcode {
    ADDITION, MULTIPLICATION, INPUT, OUTPUT, TERMINATION;

    companion object {
        fun of(opcode: Int) = when (opcode) {
            1 -> ADDITION
            2 -> MULTIPLICATION
            3 -> INPUT
            4 -> OUTPUT
            99 -> TERMINATION
            else -> throw IllegalArgumentException("Invalid opcode: $opcode")
        }
    }
}

enum class ParameterMode {
    POSITION, IMMEDIATE;

    companion object {
        fun of(parameterMode: Int) = when (parameterMode) {
            0 -> POSITION
            1 -> IMMEDIATE
            else -> throw IllegalArgumentException("Invalid parameter mode: $parameterMode")
        }
    }
}

class Instruction(
    val opcode: Opcode,
    val modeOfFirstParameter: ParameterMode,
    val modeOfSecondParameter: ParameterMode
)

object IntcodeComputer {

    private const val START_POINTER = 0
    private val inputReader = Scanner(System.`in`)

    fun execute(program: IntArray) = execute(program, START_POINTER)

    private fun execute(program: IntArray, pointer: Int) {
        val instruction = readInstruction(program[pointer])
        if (instruction.opcode == Opcode.TERMINATION) return

        val instructionLength = executeInstruction(instruction, program, pointer)
        execute(program, pointer + instructionLength)
    }

    private fun readInstruction(intcode: Int): Instruction {
        val opcode = Opcode.of(intcode % 100)
        val modeOfFirstParameter = ParameterMode.of(intcode % 1000 / 100)
        val modeOfSecondParameter = ParameterMode.of(intcode % 10000 / 1000)

        return Instruction(opcode, modeOfFirstParameter, modeOfSecondParameter)
    }

    private fun executeInstruction(instruction: Instruction, program: IntArray, pointer: Int): Int =
        when (instruction.opcode) {
            Opcode.ADDITION -> {
                val x = deriveParameter(pointer + 1, instruction.modeOfFirstParameter, program)
                val y = deriveParameter(pointer + 2, instruction.modeOfSecondParameter, program)
                val result = x + y

                val destination = program[pointer + 3]
                program[destination] = result

                4
            }
            Opcode.MULTIPLICATION -> {
                val x = deriveParameter(pointer + 1, instruction.modeOfFirstParameter, program)
                val y = deriveParameter(pointer + 2, instruction.modeOfSecondParameter, program)
                val result = x * y

                val destination = program[pointer + 3]
                program[destination] = result

                4
            }
            Opcode.INPUT -> {
                val destination = program[pointer + 1]
                val inputValue = inputReader.nextInt()
                program[destination] = inputValue

                2
            }
            Opcode.OUTPUT -> {
                val output = deriveParameter(pointer + 1, instruction.modeOfFirstParameter, program)
                println("$output (pointer: $pointer)")

                2
            }
            Opcode.TERMINATION -> 0
        }

    private fun deriveParameter(indexOfParameter: Int, mode: ParameterMode, program: IntArray) = when (mode) {
        ParameterMode.IMMEDIATE -> program[indexOfParameter]
        ParameterMode.POSITION -> {
            val position = program[indexOfParameter]
            program[position]
        }
    }
}
