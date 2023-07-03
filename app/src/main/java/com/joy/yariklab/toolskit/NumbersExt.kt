package com.joy.yariklab.toolskit

fun Long.nullIfZero(): Long? {
    if (this == 0L) return null
    return this
}

fun Long?.ifNullOrZero(action: () -> Long): Long {
    if (this == null) return action()
    if (this == 0L) return action()
    return this
}