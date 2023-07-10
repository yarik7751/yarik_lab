package com.joy.yariklab.features.music

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joy.yariklab.navigation.FlowCoordinator


@Composable
fun MusicScreen(
    viewModel: MusicViewModel,
    flowCoordinator: FlowCoordinator,
) {
    MusicScreenInfo()
}

@Composable
fun MusicScreenInfo() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Music")
    }
}

@Preview
@Composable
fun MusicScreenPreview() {
    MusicScreenInfo()
}