package com.joy.yariklab.core.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class UsersRow<Key, Value>(
    val key: Key,
    val values: List<Value>,
)

class InMemoryCache<Key, Value>(
    startValue: Map<Key, List<Value>> = hashMapOf(),
) {

    private val mutex = Mutex()

    private val _namesFlow = MutableStateFlow(startValue)
    private val namesFlow: Flow<Map<Key, List<Value>>> = _namesFlow.asStateFlow()

    suspend fun addName(
        key: Key,
        data: Value,
    ) {
        mutex.withLock {
            val value = getMutableMap()
            val names = value[key].orEmpty()
            value[key] = names.plus(data)
            _namesFlow.emit(value)
        }
    }

    suspend fun removeName(
        key: Key,
        data: Value,
    ) {
        mutex.withLock {
            val value = getMutableMap()
            val names = value[key].orEmpty().filter { it != data }
            if (names.isEmpty()) {
                value.remove(key)
            } else {
                value[key] = names
            }
            _namesFlow.emit(value)
        }
    }

    suspend fun remove(key: Key) {
        mutex.withLock {
            val value = getMutableMap()
            value.remove(key)
            _namesFlow.emit(value)
        }
    }

    suspend fun replaceAll(newData: Map<Key, List<Value>>) {
        _namesFlow.emit(newData)
    }

    fun subscribeOn(key: Key): Flow<UsersRow<Key, Value>> = namesFlow.map {
        UsersRow(
            key = key,
            values = it[key].orEmpty(),
        )
    }.distinctUntilChanged()

    private fun getMutableMap(): MutableMap<Key, List<Value>> {
        return _namesFlow.value.toMutableMap()
    }
}