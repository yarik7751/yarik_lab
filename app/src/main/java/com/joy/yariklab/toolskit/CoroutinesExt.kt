package com.joy.yariklab.toolskit

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Note that we are adding a coroutineScope because we need to wrap async in a suspend function.
 */
suspend fun <A, B> Iterable<A>.parallelMap(action: suspend (A) -> B): List<B> = coroutineScope {
    map {
        async {
            action(it)
        }
    }.awaitAll()
    /**
     * Or we could go through the list and run await() for each.
     */
}