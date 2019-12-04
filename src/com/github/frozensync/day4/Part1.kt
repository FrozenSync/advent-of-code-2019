package com.github.frozensync.day4

fun main() {
    (272091..815432)
        .asSequence()
        .filter { it.containsIdenticalPair() }
        .filter { it.isNonDecreasing() }
        .count()
        .let { println(it) }
}

fun Int.containsIdenticalPair(): Boolean {
    val digits = this.toString().map(Character::getNumericValue)

    for (i in 1 until digits.size) {
        if (digits[i] == digits[i - 1]) return true
    }

    return false
}

fun Int.isNonDecreasing(): Boolean {
    val digits = this.toString().map(Character::getNumericValue)

    for (i in 1 until digits.size) {
        if (digits[i - 1] > digits[i]) return false
    }

    return true
}
