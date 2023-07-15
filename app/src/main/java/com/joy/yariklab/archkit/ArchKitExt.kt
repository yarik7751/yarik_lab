package com.joy.yariklab.archkit

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.safeLaunch(
    errorHandler: suspend (Throwable) -> Unit,
    action: suspend (CoroutineScope) -> Unit,
) {
    launch(CoroutineExceptionHandler { _, throwable ->
        this.launch {
            errorHandler(throwable)
        }
    }) {
        action(this)
    }
}