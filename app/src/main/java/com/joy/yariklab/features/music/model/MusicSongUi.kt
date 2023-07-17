package com.joy.yariklab.features.music.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicSongUi(
    val status: SongStatus,
    val url: String,
    val title: String,
    val subtitle: String,
    val minProcess: Long,
    val maxProcess: Long,
    val currentProcess: Long,
    val isProgressVisible: Boolean,
) : Parcelable

enum class SongStatus {
    PLAY,
    PAUSE,
}
