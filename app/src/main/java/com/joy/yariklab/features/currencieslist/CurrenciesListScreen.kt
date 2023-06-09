package com.joy.yariklab.features.currencieslist

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joy.yariklab.features.currencieslist.CurrenciesListViewModel.Event
import com.joy.yariklab.features.currencieslist.model.CurrencyUi
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.uikit.LabProgressBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CurrenciesList(
    viewModel: CurrenciesListViewModel,
    flowCoordinator: FlowCoordinator,
) {
    val state = viewModel.viewState.collectAsState()

    if (state.value.isLoading) {
        LabProgressBar()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RefreshButton(viewModel)
        CurrencyList(
            state = state,
            viewModel = viewModel,
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.singleEvents.collectLatest { event ->
            when (event) {
                is Event.GoToCurrencyDetails -> {
                    flowCoordinator.goToCurrencyDetails(event.currencyCode)
                }
            }
        }
    }
}

@Composable
fun RefreshButton(viewModel: CurrenciesListViewModel) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 4.dp,
            ),
        onClick = {
            viewModel.onRefreshClick()
        }
    ) {
        Text("Refresh")
    }
}

@Composable
fun CurrencyList(
    state: State<CurrenciesListViewModel.ViewState>,
    viewModel: CurrenciesListViewModel,
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
            state.value.currencies.forEachIndexed { index, currency ->
                CurrencyListItem(
                    index = index,
                    currency = currency,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun CurrencyListItem(
    index: Int,
    currency: CurrencyUi,
    viewModel: CurrenciesListViewModel,
) {
    val backgroundColor = when {
        index % 2 == 0 -> Color.Yellow
        else -> Color.Transparent
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable {
                viewModel.onCurrencyClick(currency.code)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = currency.code,
            )
            Text(currency.title)
        }
    }
}