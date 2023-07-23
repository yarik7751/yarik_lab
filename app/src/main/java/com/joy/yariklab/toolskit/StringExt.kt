package com.joy.yariklab.toolskit

const val EMPTY_STRING = ""

fun String?.ifNull(action: () -> String): String {
    if (this != null) return this
    return action()
}

fun String?.ifNullOrEmpty(action: () -> String): String {
    return when {
        !this.isNullOrBlank() -> this
        else -> action()
    }
}