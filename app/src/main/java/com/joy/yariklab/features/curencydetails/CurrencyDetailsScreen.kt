package com.joy.yariklab.features.curencydetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joy.yariklab.navigation.FlowCoordinator

@Composable
fun CurrencyDetailsScreen(
    viewModel: CurrencyDetailsViewModel,
    flowCoordinator: FlowCoordinator,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("CurrencyDetailsScreen")
    }
}