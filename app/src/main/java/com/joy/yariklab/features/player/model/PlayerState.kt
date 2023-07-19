package com.joy.yariklab.features.player.model

import com.joy.yariklab.features.music.model.MusicSongUi

sealed interface PlayerState {

    data class Play(val song: MusicSongUi) : PlayerState
    data class Pause(val song: MusicSongUi) : PlayerState
    data class End(val song: MusicSongUi) : PlayerState
    data class Other(val song: MusicSongUi) : PlayerState

    data class Progress(
        val value: Float,
        val song: MusicSongUi,
    ) : PlayerState
}