package com.joy.yariklab.toolskit

fun Long.nullIfZero(): Long? {
    if (this == 0L) return null
    return this
}

fun Long?.ifNullOrZero(action: () -> Long): Long {
    return when {
        this != null && this != 0L -> this
        else -> action()
    }
}

fun Long?.orZero() = this ?: 0L