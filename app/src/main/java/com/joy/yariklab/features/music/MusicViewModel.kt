package com.joy.yariklab.features.music

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.archkit.safeLaunch
import com.joy.yariklab.core.domain.interactor.MusicInteractor
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.music.MusicViewModel.Event
import com.joy.yariklab.features.music.MusicViewModel.ViewState
import com.joy.yariklab.features.music.model.MusicSongUi
import com.joy.yariklab.features.music.model.SongStatus
import com.joy.yariklab.toolskit.parallelMap

class MusicViewModel(
    private val musicInteractor: MusicInteractor,
    private val errorEmitter: ErrorEmitter,
    private val exoPlayer: ExoPlayer,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val songs: List<MusicSongUi> = emptyList(),
    )

    sealed interface Event {
        // TODO
    }

    init {
        // TODO test data
        viewModelScope.safeLaunch(errorEmitter::emit) {
            val songs = musicInteractor.getSongs().parallelMap {
                val mmr = MediaMetadataRetriever()
                mmr.setDataSource(it)

                val bitmap = mmr.embeddedPicture?.let { pictureArray ->
                    BitmapFactory.decodeByteArray(pictureArray, 0, pictureArray.size)
                }
                MusicSongUi(
                    status = SongStatus.PAUSE,
                    title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                        .orEmpty(),
                    subtitle = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                        .orEmpty(),
                    url = it,
                    icon = bitmap,
                    minProcess = 0,
                    maxProcess = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                        ?.toLongOrNull() ?: 0,
                    currentProcess = 0,
                    isProgressVisible = false,
                )
            }

            reduce {
                it.copy(
                    songs = songs,
                )
            }
        }
    }

    fun onSongStatusClick(url: String) {
        // TODO test code. Need to create foreground service
        if (exoPlayer.isPlaying) {
            exoPlayer.stop()
        }
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }
}