package com.joy.yariklab.features.player.model

import android.os.Parcelable
import com.joy.yariklab.features.music.model.MusicSongUi
import kotlinx.parcelize.Parcelize

sealed interface PlayerCommand : Parcelable {
    @Parcelize
    data class Play(val song: MusicSongUi) : PlayerCommand

    @Parcelize
    object Pause : PlayerCommand

    @Parcelize
    object Stop : PlayerCommand

    @Parcelize
    data class ToPosition(val position: Float) : PlayerCommand

    @Parcelize
    object Nothing : PlayerCommand
}