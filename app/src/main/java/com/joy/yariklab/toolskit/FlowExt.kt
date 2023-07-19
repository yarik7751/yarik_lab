package com.joy.yariklab.toolskit

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun intervalTasks(
    initDelay: Long,
    delay: Long,
    isClosed: Boolean
) = flow {
    if (initDelay > 0) {
        delay(initDelay)
        while (isClosed.not()) {
            emit(System.currentTimeMillis())
            delay(delay)
        }
    }
}