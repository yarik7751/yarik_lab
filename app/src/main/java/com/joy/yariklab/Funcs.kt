package com.joy.yariklab

private const val MAX = 100_000

fun solution(array: IntArray): Int {
    if (array.isEmpty()) throw RuntimeException("Empty array!")

    val positiveArray = array.filter { it >= 1 }

    if (positiveArray.isEmpty()) return 1

    for (i in 1 .. MAX) {
        if (positiveArray.contains(i).not()) return i
    }

    throw RuntimeException("Negative value")
}

fun main() {
    val result = solution(
        listOf(1, 3, 6, 4, 1, 2).toIntArray()
    )

    println(result)
}