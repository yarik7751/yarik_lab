package com.joy.yariklab.features.music

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joy.yariklab.R
import com.joy.yariklab.features.music.model.MusicSongUi
import com.joy.yariklab.features.music.model.SongStatus
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.toolskit.EMPTY_STRING
import com.joy.yariklab.ui.theme.Purple80
import com.joy.yariklab.uikit.LabProgressBar
import com.joy.yariklab.uikit.itemCountPreview
import com.joy.yariklab.uikit.simplePadding
import kotlinx.coroutines.flow.collectLatest


@Composable
fun MusicScreen(
    viewModel: MusicViewModel,
    flowCoordinator: FlowCoordinator,
) {
    val state = viewModel.viewState.collectAsState()

    if (state.value.isLoading) {
        LabProgressBar()
    }

    MusicScreenInfo(
        songs = state.value.songs,
        viewModel = viewModel,
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.singleEvents.collectLatest { event ->
            when (event) {
                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun MusicScreenPreview() {
    MusicScreenInfo(
        songs = MusicSongUi(
            status = SongStatus.PAUSE,
            title = "Music title",
            subtitle = "Subtitle",
            icon = EMPTY_STRING,
            minProcess = 0,
            maxProcess = 10000,
            currentProcess = 0,
            isProgressVisible = false,
            url = "https://console.firebase.google.com/",
        ).itemCountPreview(4),
    )
}

@Composable
fun MusicScreenInfo(
    songs: List<MusicSongUi>,
    viewModel: MusicViewModel? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(ScrollState(0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                songs.forEach { song ->
                    MusicItem(
                        song = song,
                        viewModel = requireNotNull(viewModel),
                    )
                }
            }
        }
    }
}

@Composable
fun MusicItem(
    song: MusicSongUi,
    viewModel: MusicViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .simplePadding(
                vertical = 8.dp,
                horizontal = 8.dp,
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(color = Purple80),
        ) {
            Row(
                modifier = Modifier
                    .simplePadding(
                        vertical = 8.dp,
                    ),
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .simplePadding(
                            start = 8.dp,
                        )
                        .clickable {
                            viewModel.onSongStatusClick(song.url)
                        },
                    painter = painterResource(id = R.drawable.ic_play_white),
                    contentDescription = null,
                )

                Column(
                    modifier = Modifier
                        .simplePadding(
                            start = 8.dp,
                        )
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = song.title,
                    )
                    Text(song.subtitle)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.End)
                            .simplePadding(
                                end = 8.dp,
                            ),
                        painter = painterResource(id = R.drawable.ic_toc_white),
                        contentDescription = null,
                        alignment = Alignment.Center,
                    )
                }
            }

            if (song.isProgressVisible.not()) return@Column

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .simplePadding(
                        horizontal = 8.dp,
                    ),
            ) {
                Slider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = 100F,
                    onValueChange = {},
                )
            }
        }
    }
}