package com.github.frozensync.day5

import java.util.*

enum class Opcode {
    ADDITION, MULTIPLICATION, INPUT, OUTPUT, JUMP_IF_TRUE, JUMP_IF_FALSE, LESS_THAN, EQUALS, TERMINATION;

    companion object {
        fun of(opcode: Int) = when (opcode) {
            1 -> ADDITION
            2 -> MULTIPLICATION
            3 -> INPUT
            4 -> OUTPUT
            5 -> JUMP_IF_TRUE
            6 -> JUMP_IF_FALSE
            7 -> LESS_THAN
            8 -> EQUALS
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

        val newPointer = executeInstruction(instruction, program, pointer)
        execute(program, newPointer)
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

                pointer + 4
            }
            Opcode.MULTIPLICATION -> {
                val x = deriveParameter(pointer + 1, instruction.modeOfFirstParameter, program)
                val y = deriveParameter(pointer + 2, instruction.modeOfSecondParameter, program)
                val result = x * y

                val destination = program[pointer + 3]
                program[destination] = result

                pointer + 4
            }
            Opcode.INPUT -> {
                val destination = program[pointer + 1]
                val inputValue = inputReader.nextInt()
                program[destination] = inputValue

                pointer + 2
            }
            Opcode.OUTPUT -> {
                val output = deriveParameter(pointer + 1, instruction.modeOfFirstParameter, program)
                println(output)

                pointer + 2
            }
            Opcode.JUMP_IF_TRUE -> {
                val jump = deriveParameter(pointer + 1, instruction.modeOfFirstParameter, program)

                if (jump == 0) {
                    pointer + 3
                } else {
                    deriveParameter(pointer + 2, instruction.modeOfSecondParameter, program)
                }
            }
            Opcode.JUMP_IF_FALSE -> {
                val jump = deriveParameter(pointer + 1, instruction.modeOfFirstParameter, program)

                if (jump == 0) {
                    deriveParameter(pointer + 2, instruction.modeOfSecondParameter, program)
                } else {
                    pointer + 3
                }
            }
            Opcode.LESS_THAN -> {
                val x = deriveParameter(pointer + 1, instruction.modeOfFirstParameter, program)
                val y = deriveParameter(pointer + 2, instruction.modeOfFirstParameter, program)
                val result = if (x < y) 1 else 0

                val destination = program[pointer + 3]
                program[destination] = result

                pointer + 4
            }
            Opcode.EQUALS -> {
                val x = deriveParameter(pointer + 1, instruction.modeOfFirstParameter, program)
                val y = deriveParameter(pointer + 2, instruction.modeOfFirstParameter, program)
                val result = if (x == y) 1 else 0

                val destination = program[pointer + 3]
                program[destination] = result

                pointer + 4
            }
            Opcode.TERMINATION -> throw IllegalStateException("Termination code not caught at pointer $pointer")
        }

    private fun deriveParameter(indexOfParameter: Int, mode: ParameterMode, program: IntArray) = when (mode) {
        ParameterMode.IMMEDIATE -> program[indexOfParameter]
        ParameterMode.POSITION -> {
            val position = program[indexOfParameter]
            program[position]
        }
    }
}
