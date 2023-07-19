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
import com.joy.yariklab.features.player.model.PlayerCommand
import com.joy.yariklab.features.player.model.PlayerState
import com.joy.yariklab.features.player.observer.PlayerObserver
import com.joy.yariklab.toolskit.EMPTY_STRING
import com.joy.yariklab.toolskit.parallelMap
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MusicViewModel(
    private val musicInteractor: MusicInteractor,
    private val errorEmitter: ErrorEmitter,
    private val playerObserver: PlayerObserver,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val songs: List<MusicSongUi> = emptyList(),
        val selectedUrl: String = EMPTY_STRING,
    )

    sealed interface Event {
        data class SendCommandToPlayer(val command: PlayerCommand) : Event
    }

    init {
        viewModelScope.launch {
            playerObserver.subscribeOnPlayerState()
                .onEach { state ->
                    when (state) {
                        is PlayerState.End -> {
                            var nextIndex = -1
                            var selectedSong: MusicSongUi? = null
                            val newSongs = stateValue.songs.mapIndexed { index, song ->
                                when {
                                    song.url == state.song.url -> {
                                        nextIndex = index + 1
                                        song.copy(status = SongStatus.UNSELECT)
                                    }
                                    index == nextIndex -> {
                                        song.copy(status = changeSongStatus(song.status)).apply {
                                            selectedSong = this
                                        }
                                    }
                                    else -> song
                                }
                            }

                            this@MusicViewModel.reduce {
                                it.copy(songs = newSongs)
                            }

                            selectedSong?.let {
                                val command = when (it.status) {
                                    SongStatus.PLAY-> PlayerCommand.Play(it)
                                    SongStatus.PAUSE -> PlayerCommand.Pause
                                    SongStatus.UNSELECT -> PlayerCommand.Nothing
                                }

                                sendEvent(Event.SendCommandToPlayer(command))
                            }
                        }
                        is PlayerState.Other -> {}
                        is PlayerState.Pause -> {}
                        is PlayerState.Play -> {}
                        is PlayerState.Progress -> {
                            stateValue.songs.map { song ->
                                if (song.url == state.song.url) {
                                    song.copy(currentProcess = state.value)
                                } else {
                                    song
                                }
                            }.let { songs ->
                                reduce {
                                    it.copy(songs = songs)
                                }
                            }
                        }
                    }
                }
                .launchIn(this)
        }

        viewModelScope.safeLaunch(errorEmitter::emit) {
            val songs = musicInteractor.getSongs().parallelMap {
                val mmr = MediaMetadataRetriever()
                mmr.setDataSource(it)

                /*val bitmap = mmr.embeddedPicture?.let { pictureArray ->
                    BitmapFactory.decodeByteArray(pictureArray, 0, pictureArray.size)
                }*/
                MusicSongUi(
                    status = SongStatus.UNSELECT,
                    title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                        .orEmpty(),
                    subtitle = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                        .orEmpty(),
                    url = it,
                    minProcess = 0F,
                    maxProcess = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                        ?.toFloatOrNull() ?: 0F,
                    currentProcess = 0F,
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
            var selectedSong: MusicSongUi? = null

            val newSongs = viewState.value.songs.map { song ->
                when (song.url) {
                    viewState.value.selectedUrl -> {
                        song.copy(status = changeSongStatus(song.status)).apply {
                            selectedSong = this
                        }
                    }
                    else -> song.copy(status = SongStatus.UNSELECT)
                }
            }

            this@MusicViewModel.reduce {
                it.copy(songs = newSongs)
            }

            selectedSong?.let {
                val command = when (it.status) {
                    SongStatus.PLAY-> PlayerCommand.Play(it)
                    SongStatus.PAUSE -> PlayerCommand.Pause
                    SongStatus.UNSELECT -> PlayerCommand.Nothing
                }

                sendEvent(Event.SendCommandToPlayer(command))
            }
        }
    }

    private fun changeSongStatus(currentStatus: SongStatus): SongStatus {
        return when (currentStatus) {
            SongStatus.PLAY -> SongStatus.PAUSE
            SongStatus.PAUSE,
            SongStatus.UNSELECT-> SongStatus.PLAY
        }
    }

    fun onPositionChanged(newPosition: Float) {
        viewModelScope.launch {
            sendEvent(Event.SendCommandToPlayer(PlayerCommand.ToPosition(newPosition)))
        }
    }
}