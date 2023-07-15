package com.joy.yariklab.features.music.model

data class MusicSongUi(
    val status: SongStatus,
    val url: String,
    val title: String,
    val subtitle: String,
    // TODO need to investigate, how we need to get icon
    val icon: String,
    val minProcess: Long,
    val maxProcess: Long,
    val currentProcess: Long,
    val isProgressVisible: Boolean,
)

enum class SongStatus {
    PLAY,
    PAUSE,
}
