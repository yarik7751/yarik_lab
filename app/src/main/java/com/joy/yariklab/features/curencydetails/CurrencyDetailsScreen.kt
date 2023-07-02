package com.joy.yariklab.features.curencydetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joy.yariklab.features.curencydetails.model.CurrencyDetailsUi
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.uikit.LabProgressBar
import com.joy.yariklab.uikit.simplePadding

@Composable
fun CurrencyDetailsScreen(
    viewModel: CurrencyDetailsViewModel,
    flowCoordinator: FlowCoordinator,
) {
    val state = viewModel.viewState.collectAsState()

    if (state.value.isLoading) {
        LabProgressBar()
    }

    state.value.currencyDetailsUi?.let {
        CurrencyDetailsInfo(it)
    }
}

@Preview
@Composable
fun CurrencyDetailsScreenPreview() {
    CurrencyDetailsInfo(
        CurrencyDetailsUi(
            code = "CODE",
            currency = "title of currency",
            rates = listOf(
                CurrencyDetailsUi.Rate(
                    no = "test no",
                    mid = 34.45,
                    effectiveDate = "2022-04-19",
                )
            )
        )
    )
}

@Composable
fun CurrencyDetailsInfo(currencyDetails: CurrencyDetailsUi) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        Row {
            Text(
                modifier = Modifier
                    .simplePadding(
                        start = 8.dp,
                    ),
                fontWeight = FontWeight.Bold,
                text = currencyDetails.code,
            )
            Text(
                modifier = Modifier
                    .simplePadding(
                        start = 8.dp,
                    ),
                text = currencyDetails.currency,
            )
        }

        CurrencyRatesList(currencyDetails.rates)
    }
}

@Composable
fun CurrencyRatesList(rates: List<CurrencyDetailsUi.Rate>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(ScrollState(0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            rates.forEach { rate ->
                CurrencyRateItem(rate)
            }
        }
    }
}

@Composable
fun CurrencyRateItem(rate: CurrencyDetailsUi.Rate) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        Text(
            modifier = Modifier
                .simplePadding(start = 8.dp),
            text = "Number: ${rate.no}",
        )
        Text(
            modifier = Modifier
                .simplePadding(start = 8.dp),
            text = "Rate: ${rate.mid}",
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.End)
                .simplePadding(end = 8.dp),
            text = rate.effectiveDate,
        )
    }
}