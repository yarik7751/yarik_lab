package com.joy.yariklab.features.player.observer

import com.joy.yariklab.features.player.model.PlayerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface PlayerEmitter {

    fun send(state: PlayerState)
}

interface PlayerObserver {

    suspend fun subscribeOnPlayerState(): Flow<PlayerState>
}

class PlayerObserverImpl : PlayerEmitter, PlayerObserver {

    private val _playerStateFlow = MutableSharedFlow<PlayerState>(
        extraBufferCapacity = 3,
    )

    override fun send(state: PlayerState) {
        _playerStateFlow.tryEmit(state)
    }

    override suspend fun subscribeOnPlayerState(): Flow<PlayerState> {
        return _playerStateFlow
    }
}