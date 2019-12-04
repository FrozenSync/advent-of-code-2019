package com.github.frozensync.day4

fun main() {
    (272091..815432)
        .asSequence()
        .filter { it.containsExactIdenticalPair() }
        .filter { it.isNonDecreasing() }
        .count()
        .let { println(it) }
}

fun Int.containsExactIdenticalPair(): Boolean {
    val digits = this.toString().map(Character::getNumericValue)

    return when (val length = digits.size) {
        1 -> false
        2 -> digits[0] == digits[1]
        else -> {
            for (i in 1 until length) {
                if (digits[i] == digits[i - 1]
                    && !containsIdenticalTrail(digits, i - 1)
                    && !containsIdenticalLead(digits, i)
                )
                    return true
            }
            false
        }
    }
}

private fun containsIdenticalTrail(digits: List<Int>, index: Int) =
    if (index - 1 < 0) false else digits[index] == digits[index - 1]

private fun containsIdenticalLead(digits: List<Int>, index: Int) =
    if (index + 1 >= digits.size) false else digits[index] == digits[index + 1]
