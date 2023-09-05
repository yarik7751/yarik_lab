package com.joy.yariklab.features.userlist

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joy.yariklab.features.common.logo.StartTitle
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.uikit.LabProgressBar
import com.joy.yariklab.uikit.simplePadding
import kotlinx.coroutines.flow.collectLatest

@Composable
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
fun UserListScreenPreview() {
    UserListInfo(
        background = Color.White,
        state = UserListViewModel.ViewState()
    )
}

@Composable
fun UserListInfo(
    background: Color = Color.Transparent,
    viewModel: UserListViewModel? = null,
    state: UserListViewModel.ViewState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(ScrollState(0))
            .simplePadding(
                horizontal = 8.dp
            ),
    ) {
        StartTitle {
            this.align(Alignment.CenterHorizontally)
        }
    }
}