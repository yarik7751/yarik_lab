package com.joy.yariklab.features.userlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.joy.yariklab.R
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.ui.theme.DefaultBackground
import com.joy.yariklab.ui.theme.Green
import com.joy.yariklab.ui.theme.Pink80
import com.joy.yariklab.ui.theme.Red
import com.joy.yariklab.ui.theme.White
import com.joy.yariklab.uikit.LabProgressBar
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

    val context = LocalContext.current
    val exoPlayer = remember {
        val render = DefaultRenderersFactory(context).setEnableDecoderFallback(true)
        ExoPlayer.Builder(context, render)
            .build()
    }

    UserListInfo(
        background = DefaultBackground,
        viewModel = viewModel,
        state = state.value,
        exoPlayer = exoPlayer,
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
        background = DefaultBackground,
        state = UserListViewModel.ViewState(),
        exoPlayer = null,
    )
}

@Composable
@androidx.media3.common.util.UnstableApi
fun UserListInfo(
    background: Color,
    viewModel: UserListViewModel? = null,
    state: UserListViewModel.ViewState,
    exoPlayer: ExoPlayer?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
    ) {
        UsersToolBar()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(
                    color = White,
                    shape = RoundedCornerShape(5),
                ),
        ) {
            if (exoPlayer != null) {
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
            } else {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    painter = painterResource(id = R.drawable.ic_person_white),
                    contentDescription = "Mock image",
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
                .background(
                    color = White,
                    shape = RoundedCornerShape(50),
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(vertical = 12.dp)
                        .padding(start = 12.dp),
                    onClick = {
                        viewModel?.onLikeClick()
                    },
                    containerColor = Green,
                    shape = CircleShape,
                ) {
                    Icon(Icons.Filled.Done, "Like action")
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(vertical = 12.dp)
                        .padding(end = 12.dp),
                    onClick = {
                        viewModel?.onSkipClick()
                    },
                    containerColor = Red,
                    shape = CircleShape,
                ) {
                    Icon(Icons.Filled.Close, "Skip action")
                }
            }
        }
    }
}

@Composable
fun UsersToolBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .background(
                color = Pink80,
                shape = RoundedCornerShape(50),
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .padding(start = 12.dp)
                .clickable {
                    // TODO go to Settings
                },
            painter = painterResource(id = R.drawable.ic_user_settings_white),
            contentDescription = "User settings",
        )

        Text(
            modifier = Modifier.weight(1F),
            text = "Peoples",
            textAlign = TextAlign.Center,
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

        Image(
            modifier = Modifier
                .padding(end = 12.dp)
                .clickable {
                    // TODO go to Profile
                },
            painter = painterResource(id = R.drawable.ic_person_white),
            contentDescription = "User settings",
        )
    }
}

@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer?,
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