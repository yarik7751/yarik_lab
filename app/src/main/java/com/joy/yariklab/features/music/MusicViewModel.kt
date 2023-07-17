package com.joy.yariklab.features.music

import android.media.MediaMetadataRetriever
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.archkit.safeLaunch
import com.joy.yariklab.core.domain.interactor.MusicInteractor
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.music.MusicViewModel.Event
import com.joy.yariklab.features.music.MusicViewModel.ViewState
import com.joy.yariklab.features.music.model.MusicSongUi
import com.joy.yariklab.features.music.model.SongStatus
import com.joy.yariklab.toolskit.EMPTY_STRING
import com.joy.yariklab.toolskit.parallelMap
import kotlinx.coroutines.launch

class MusicViewModel(
    private val musicInteractor: MusicInteractor,
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val songs: List<MusicSongUi> = emptyList(),
        val selectedUrl: String = EMPTY_STRING,
    )

    sealed interface Event {
        data class StartPalyer(val song: MusicSongUi) : Event
    }

    init {
        viewModelScope.safeLaunch(errorEmitter::emit) {
            val songs = musicInteractor.getSongs().parallelMap {
                val mmr = MediaMetadataRetriever()
                mmr.setDataSource(it)

                /*val bitmap = mmr.embeddedPicture?.let { pictureArray ->
                    BitmapFactory.decodeByteArray(pictureArray, 0, pictureArray.size)
                }*/
                MusicSongUi(
                    status = SongStatus.PAUSE,
                    title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                        .orEmpty(),
                    subtitle = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                        .orEmpty(),
                    url = it,
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

    fun onSongStatusClick(
        url: String,
        isPermissionGranted: Boolean
    ) {
        viewModelScope.launch {
            this@MusicViewModel.reduce {
                it.copy(selectedUrl = url)
            }

            if (isPermissionGranted) {
                onCheckNotificationPermission()
            }
        }
    }

    fun onCheckNotificationPermission() {
        viewModelScope.launch {
            viewState.value.songs.find { it.url == viewState.value.selectedUrl }?.let {
                sendEvent(Event.StartPalyer(it))
            }
        }
    }
}