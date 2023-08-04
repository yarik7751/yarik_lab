package com.joy.yariklab.features.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joy.yariklab.R
import com.joy.yariklab.features.start.StartViewModel.Event
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.ui.theme.DefaultButton
import com.joy.yariklab.ui.theme.Pink80
import com.joy.yariklab.uikit.LabProgressBar
import com.joy.yariklab.uikit.simplePadding
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StartScreen(
    viewModel: StartViewModel,
    flowCoordinator: FlowCoordinator,
) {
    val state = viewModel.viewState.collectAsState()

    if (state.value.isLoading) {
        LabProgressBar()
    }

    StartInfo(viewModel)

    LaunchedEffect(key1 = Unit) {
        viewModel.singleEvents.collectLatest { event ->
            when (event) {
                Event.GoToLoginScreen -> {
                    flowCoordinator.goToLogin()
                }
            }
        }
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    StartInfo(
        background = Color.White,
    )
}

@Composable
fun StartInfo(
    viewModel: StartViewModel? = null,
    background: Color = Color.Transparent,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
    ) {
        StartTitle()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .simplePadding(
                    horizontal = 8.dp,
                )
                .align(Alignment.Center),
        ) {
            StartActions(viewModel)
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .simplePadding(
                    horizontal = 8.dp,
                    bottom = 8.dp,
                ),
            text = stringResource(id = R.string.start_description),
        )
    }
}

@Composable
fun BoxScope.StartTitle() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .align(Alignment.TopCenter),
    ) {
        Text(
            modifier = Modifier
                .simplePadding(
                    horizontal = 8.dp,
                    bottom = 8.dp,
                )
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.start_title),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .simplePadding(
                    horizontal = 8.dp,
                    bottom = 8.dp,
                )
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.start_title_sense),
            fontSize = 25.sp,
            color = Pink80
        )
    }
}

@Composable
fun StartActions(
    viewModel: StartViewModel?,
) {
    DefaultButton(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            viewModel?.onSignInClick()
        }
    ) {
        Text(text = stringResource(id = R.string.start_login))
    }

    DefaultButton(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            // TODO got to registration flow
        }
    ) {
        Text(text = stringResource(id = R.string.start_register))
    }
}