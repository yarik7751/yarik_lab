package com.joy.yariklab.features.music.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicSongUi(
    val status: SongStatus,
    val url: String,
    val title: String,
    val subtitle: String,
    val minProcess: Float,
    val maxProcess: Float,
    val currentProcess: Float,
) : Parcelable

enum class SongStatus {
    UNSELECT,
    PLAY,
    PAUSE,
}
