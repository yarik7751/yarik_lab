package com.joy.yariklab.core.cache

import com.joy.yariklab.UsersRow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryCache/*<Key, Value>*/ {

    private val mutex = Mutex()

    private val _namesFlow = MutableStateFlow<Map<Int, List<String>>>(hashMapOf())
    private val namesFlow: Flow<Map<Int, List<String>>> = _namesFlow.asStateFlow()

    suspend fun addName(
        id: Int,
        name: String,
    ) {
        mutex.withLock {
            val value = getMutableMap()
            val names = value[id].orEmpty()
            value[id] = names.plus(name)
            _namesFlow.emit(value)
        }
    }

    suspend fun removeName(
        id: Int,
        name: String,
    ) {
        mutex.withLock {
            val value = getMutableMap()
            val names = value[id].orEmpty().filter { it != name }
            if (names.isEmpty()) {
                value.remove(id)
            } else {
                value[id] = names
            }
            _namesFlow.emit(value)
        }
    }

    suspend fun remove(id: Int) {
        mutex.withLock {
            val value = getMutableMap()
            value.remove(id)
            _namesFlow.emit(value)
        }
    }

    fun subscribeOn(id: Int): Flow<UsersRow> = namesFlow.map {
        UsersRow(
            id = id,
            names = it[id].orEmpty(),
        )
    }.distinctUntilChanged()

    private fun getMutableMap(): MutableMap<Int, List<String>> {
        return _namesFlow.value.toMutableMap()
    }
}