package com.joy.yariklab

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class Users {

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

data class UsersRow(
    val id: Int,
    val names: List<String>,
)

fun main() {
    // TestExpl.yarikMain()
    val users = Users()

    val scope = CoroutineScope(Job())

    val job1 = scope.launch {
        users.subscribeOn(1)
            .collect {
                println(it)
            }
    }

    val job2 = scope.launch {
        users.subscribeOn(2)
            .collect {
                // println(it)
            }
    }

    val job = scope.launch {
        coroutineScope {
            val deferred1 = async {
                withContext(Dispatchers.IO) {
                    users.apply {
                        addName(1, "Yarik")
                        addName(1, "Vasya")
                        delay(100)
                        addName(1, "Dima")
                        addName(1, "Oleg")

                        delay(100)

                        removeName(2, "Vova")
                        removeName(2, "Zuzya")
                        remove(2)
                    }
                }
            }

            val deferred2 = async {
                withContext(Dispatchers.IO) {
                    users.apply {
                        addName(2, "Rzora")
                        addName(2, "Vova")

                        delay(50)

                        addName(2, "Zuzya")
                        removeName(1, "Yarik")
                    }
                }
            }

            deferred1.await()
            deferred2.await()
        }
    }

    runBlocking {
        job1.join()
        job2.join()

        job.join()
    }
}