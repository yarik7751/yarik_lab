package com.joy.yariklab.toolskit

val EMPTY_STRING = ""

fun String?.ifNull(action: () -> String): String {
    if (this != null) return this
    return action()
}

fun String?.ifNullOrEmpty(action: () -> String): String {
    if (this == null) return action()
    if (this.isEmpty()) return action()
    return this
}