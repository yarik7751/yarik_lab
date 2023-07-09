package com.joy.yariklab.features.currencieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.domain.interactor.CurrencyInteractor
import com.joy.yariklab.features.currencieslist.CurrenciesListViewModel.Event
import com.joy.yariklab.features.currencieslist.CurrenciesListViewModel.ViewState
import com.joy.yariklab.features.currencieslist.model.CurrencyUi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CurrenciesListViewModel(
    private val currencyInteractor: CurrencyInteractor,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val currencies: List<CurrencyUi> = emptyList(),
    )

    sealed interface Event {
        data class GoToCurrencyDetails(val currencyCode: String) : Event
    }

    init {
        initData()
        subscribeOnCurrencies()
    }

    private fun initData() {
        viewModelScope.launch {
            currencyInteractor.tryToUpdateCurrencies()
        }
    }

    private fun subscribeOnCurrencies() {
        viewModelScope.launch {
            currencyInteractor.subscribeOnCurrencies()
                .onEach { currencies ->
                    updateCurrencies(currencies)
                }
                .launchIn(this)
        }
    }

    private suspend fun updateCurrencies(currencies: List<Currency>) {
        reduce {
            it.copy(isLoading = true)
        }
        currencies.lastOrNull()?.rates.orEmpty().map { rate ->
            CurrencyUi(
                code = rate.code,
                title = rate.currency,
            )
        }.sortedBy { it.code }.let { currenciesUi ->
            reduce {
                it.copy(
                    currencies = currenciesUi,
                )
            }
        }

        reduce {
            it.copy(isLoading = false)
        }
    }

    fun onRefreshClick() {
        viewModelScope.launch {
            updateCurrencies(currencyInteractor.subscribeOnCurrencies().first())
        }
    }

    fun onCurrencyClick(code: String) {
        viewModelScope.sendEvent(Event.GoToCurrencyDetails(code))
    }
}