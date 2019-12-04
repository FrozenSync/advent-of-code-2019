package com.github.frozensync.day3

enum class Direction {
    UP, RIGHT, DOWN, LEFT
}

data class Instruction(val direction: Direction, val steps: Int)

class Instructions(private val value: List<Instruction>) {

    fun toPath(): List<Coordinate> = value.fold(mutableListOf()) { path, i -> walk(i.direction, i.steps, path) }

    private fun walk(direction: Direction, steps: Int, path: MutableList<Coordinate>): MutableList<Coordinate> {
        if (steps == 0) return path

        val last = path.lastOrNull() ?: Coordinate(0, 0)
        val step = when (direction) {
            Direction.UP -> last.copy(y = last.y + 1)
            Direction.RIGHT -> last.copy(x = last.x + 1)
            Direction.DOWN -> last.copy(y = last.y - 1)
            Direction.LEFT -> last.copy(x = last.x - 1)
        }
        path.add(step)

        return walk(direction, steps - 1, path)
    }
}
