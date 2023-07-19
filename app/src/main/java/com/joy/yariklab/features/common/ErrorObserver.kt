package com.joy.yariklab.features.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface ErrorObserver {

    suspend fun subscribeOnErrors(): Flow<Throwable>
}

interface ErrorEmitter {

    suspend fun emit(error: Throwable)
}

class ErrorObserverImpl : ErrorObserver, ErrorEmitter {

    private val _errorFlow = MutableSharedFlow<Throwable>()

    override suspend fun emit(error: Throwable) {
        _errorFlow.emit(error)
    }

    override suspend fun subscribeOnErrors(): Flow<Throwable> {
        return _errorFlow
    }
}