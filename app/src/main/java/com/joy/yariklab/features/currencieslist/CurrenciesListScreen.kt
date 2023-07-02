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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joy.yariklab.features.currencieslist.CurrenciesListViewModel.Event
import com.joy.yariklab.features.currencieslist.model.CurrencyUi
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.uikit.LabProgressBar
import com.joy.yariklab.uikit.itemCountPreview
import com.joy.yariklab.uikit.simplePadding
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CurrenciesListScreen(
    viewModel: CurrenciesListViewModel,
    flowCoordinator: FlowCoordinator,
) {
    val state = viewModel.viewState.collectAsState()

    if (state.value.isLoading) {
        LabProgressBar()
    }

    CurrenciesListInfo(
        viewModel = viewModel,
        currencies = state.value.currencies,
    )

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

@Preview
@Composable
fun CurrenciesListScreenPreview() {
    CurrenciesListInfo(
        currencies = CurrencyUi(
            code = "test code",
            title = "test title",
        ).itemCountPreview(5),
    )
}

@Composable
fun CurrenciesListInfo(
    viewModel: CurrenciesListViewModel? = null,
    currencies: List<CurrencyUi>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RefreshButton(viewModel)
        CurrencyList(
            currencies = currencies,
            viewModel = viewModel,
        )
    }
}

@Composable
fun RefreshButton(viewModel: CurrenciesListViewModel? = null) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .simplePadding(
                top = 8.dp,
                horizontal = 8.dp,
                bottom = 4.dp,
            ),
        onClick = {
            viewModel?.onRefreshClick()
        }
    ) {
        Text("Refresh")
    }
}

@Composable
fun CurrencyList(
    currencies: List<CurrencyUi>,
    viewModel: CurrenciesListViewModel? = null,
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
            currencies.forEachIndexed { index, currency ->
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
    viewModel: CurrenciesListViewModel? = null,
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
                viewModel?.onCurrencyClick(currency.code)
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