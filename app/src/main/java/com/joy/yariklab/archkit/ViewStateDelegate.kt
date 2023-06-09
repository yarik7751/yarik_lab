package com.joy.yariklab.archkit

import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.arch.core.executor.ArchTaskExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewStateDelegate<ViewState, Event> {

    val viewState: StateFlow<ViewState>

    val stateValue: ViewState

    val singleEvents: Flow<Event>

    @MainThread
    @Throws(IllegalStateException::class)
    fun CoroutineScope.reduce(action: (state: ViewState) -> ViewState)

    fun CoroutineScope.sendEvent(event: Event)

    fun CoroutineScope.tryToSendEvent(event: Event)

    suspend fun reduce(action: (state: ViewState) -> ViewState)
}

class ViewStateDelegateImpl<ViewState, Event>(
    initialState: ViewState,
    singleLiveEventCapacity: Int = Channel.BUFFERED,
) : ViewStateDelegate<ViewState, Event> {

    private val stateFlow = MutableStateFlow(initialState)

    override val viewState: StateFlow<ViewState>
        get() = stateFlow.asStateFlow()

    override val stateValue: ViewState
        get() = stateFlow.value

    private val singleEventsChannel = Channel<Event>(singleLiveEventCapacity)

    override val singleEvents: Flow<Event>
        get() = singleEventsChannel.receiveAsFlow()

    override fun CoroutineScope.reduce(action: (state: ViewState) -> ViewState) {
        launch {
            assertMainThread()
            stateFlow.emit(action(stateValue))
        }
    }

    override suspend fun reduce(action: (state: ViewState) -> ViewState) {
        stateFlow.emit(action(stateValue))
    }

    override fun CoroutineScope.sendEvent(event: Event) {
        launch {
            singleEventsChannel.send(event)
        }
    }

    override fun CoroutineScope.tryToSendEvent(event: Event) {
        launch {
            singleEventsChannel.trySend(event)
        }
    }

    @SuppressLint("RestrictedApi")
    @Throws(IllegalStateException::class)
    private fun assertMainThread() {
        check(ArchTaskExecutor.getInstance().isMainThread) {
            throw IllegalStateException("Cannot invoke on a background thread")
        }
    }
}