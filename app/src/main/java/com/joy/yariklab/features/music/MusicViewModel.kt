package com.joy.yariklab.features.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.core.domain.interactor.MusicInteractor
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.music.MusicViewModel.Event
import com.joy.yariklab.features.music.MusicViewModel.ViewState
import com.joy.yariklab.features.music.model.MusicSongUi
import com.joy.yariklab.features.music.model.SongStatus
import com.joy.yariklab.toolskit.EMPTY_STRING
import kotlinx.coroutines.launch

class MusicViewModel(
    private val musicInteractor: MusicInteractor,
    private val errorEmitter: ErrorEmitter,
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
        viewModelScope.launch {
            val testSongs = mutableListOf<MusicSongUi>()
            for (i in 1..20) {
                testSongs.add(
                    MusicSongUi(
                        status = SongStatus.PAUSE,
                        title = "Music title $i",
                        subtitle = "Subtitle $i",
                        url = "https://console.firebase.google.com/$i",
                        icon = EMPTY_STRING,
                        minProcess = 0,
                        maxProcess = 10000,
                        currentProcess = 0,
                        isProgressVisible = false,
                    )
                )
            }
            reduce {
                it.copy(
                    songs = testSongs,
                )
            }
        }
    }

    fun onSongStatusClick(url: String) {
        // TODO
    }
}