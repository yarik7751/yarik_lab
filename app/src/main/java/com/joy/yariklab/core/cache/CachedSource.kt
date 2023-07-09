package com.joy.yariklab.core.cache

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal typealias CompareKeyStrategy<T> = (T, T) -> Boolean

private fun <T> byValues(): CompareKeyStrategy<T> = { key1, key2 -> key1 != key2 }

class CachedSource<Key : Any, Value : Any>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getFromRemoteSource: suspend (params: Key) -> Value,
    private val compareKeyStrategy: CompareKeyStrategy<Key> = byValues(),
) {
    enum class Strategy {
        CACHE_ONLY,
        LOAD_IF_NO_CACHE,
        REFRESH_AND_GET,
    }

    private val cacheMutex = Mutex()

    private val state = MutableStateFlow(emptyList<Pair<Key, Value>>())

    fun subscribe(): Flow<List<Pair<Key, Value>>> = state

    fun subscribeByKey(key: Key): Flow<Value?> = state
        .map { items ->
            items.firstOrNull { pair ->
                pair.first.isEqualsKey(key)
            }?.second
        }
        .flowOn(dispatcher)

    /**
     * Get or load a result based on given parameters.
     */
    suspend fun get(key: Key, strategy: Strategy): Flow<Value?> {
        return when (strategy) {
            Strategy.CACHE_ONLY -> subscribeByKey(key)
            Strategy.REFRESH_AND_GET -> {
                flow {
                    refresh(key)
                    get(key, Strategy.CACHE_ONLY)
                }
            }
            Strategy.LOAD_IF_NO_CACHE -> {
                val cache = subscribeByKey(key).firstOrNull()
                if (cache != null) {
                    flow { emit(cache) }
                } else {
                    flow {
                        refresh(key)
                        get(key, Strategy.CACHE_ONLY)
                    }
                }
            }
        }
    }

    suspend fun refresh(key: Key) {
        val remoteItem = withContext(dispatcher) {
            getFromRemoteSource(key)
        }
        update(key, remoteItem)
    }

    suspend fun update(key: Key, value: Value) {
        cacheMutex.withLock {
            val items = state.value.toMutableList()
            val item = items.find { pair -> pair.first.isEqualsKey(key) }
            if (item != null) {
                items.remove(item)
                items.add(Pair(key, value))
                state.emit(items)
            }
        }
    }

    private fun Key.isEqualsKey(key: Key) = compareKeyStrategy.invoke(this, key)

    suspend fun clear(key: Key) {
        cacheMutex.withLock {
            val items = state.value.toMutableList()
            val item = items.find { pair -> pair.first == key }
            if (item != null) {
                items.remove(item)
                state.emit(items)
            }
        }
    }

    suspend fun clearAll() {
        cacheMutex.withLock {
            state.emit(emptyList())
        }
    }
}