package com.joy.yariklab.features.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joy.yariklab.navigation.FlowCoordinator

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    flowCoordinator: FlowCoordinator,
) {
    WeatherScreenInfo()
}

@Composable
fun WeatherScreenInfo() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Weather")
    }
}

@Preview
@Composable
fun WeatherScreenPreview() {
    WeatherScreenInfo()
}