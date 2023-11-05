package com.joy.yariklab.features.userlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.ui.theme.Green
import com.joy.yariklab.ui.theme.Red
import com.joy.yariklab.uikit.LabProgressBar
import com.joy.yariklab.uikit.simplePadding
import kotlinx.coroutines.flow.collectLatest

@Composable
@androidx.media3.common.util.UnstableApi
fun UserListScreen(
    viewModel: UserListViewModel,
    flowCoordinator: FlowCoordinator,
) {
    val state = viewModel.viewState.collectAsState()

    if (state.value.isLoading) {
        LabProgressBar()
    }

    UserListInfo(
        viewModel = viewModel,
        state = state.value,
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
@androidx.media3.common.util.UnstableApi
fun UserListScreenPreview() {
    UserListInfo(
        background = Color.White,
        state = UserListViewModel.ViewState()
    )
}

@Composable
@androidx.media3.common.util.UnstableApi
fun UserListInfo(
    background: Color = Color.Transparent,
    viewModel: UserListViewModel? = null,
    state: UserListViewModel.ViewState,
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        val render = DefaultRenderersFactory(context).setEnableDecoderFallback(true)
        ExoPlayer.Builder(context, render)
            .build()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        VideoPlayer(exoPlayer)

        val currentUser = state.currentUser
        if (currentUser != null) {
            exoPlayer.apply {
                setMediaItem(
                    MediaItem.fromUri(currentUser.videoUrl)
                )
                prepare()
                play()
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .simplePadding(
                    start = 8.dp,
                    bottom = 8.dp,
                ),
            onClick = {
                viewModel?.onLikeClick()
            },
            containerColor = Green,
        ) {
            Icon(Icons.Filled.Done, "Like action")
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .simplePadding(
                    end = 8.dp,
                    bottom = 8.dp,
                ),
            onClick = {
                viewModel?.onSkipClick()
            },
            containerColor = Red,
        ) {
            Icon(Icons.Filled.Close, "Skip action")
        }
    }
}

@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer,
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
            }
        },
        modifier = Modifier
            .fillMaxSize(),
    )
}