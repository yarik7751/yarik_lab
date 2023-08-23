package com.joy.yariklab.features.music.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicSongUi(
    val title: String,
    val subtitle: String,
    val url: String,
    val currentProcess: Float,
) : Parcelable
