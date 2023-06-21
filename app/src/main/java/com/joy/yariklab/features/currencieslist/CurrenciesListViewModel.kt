package com.joy.yariklab.features.currencieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.core.domain.CurrencyInteractor
import com.joy.yariklab.features.currencieslist.CurrenciesListViewModel.Event
import com.joy.yariklab.features.currencieslist.CurrenciesListViewModel.ViewState
import com.joy.yariklab.features.currencieslist.model.CurrencyUi
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
        onRefreshClick()
    }

    fun onRefreshClick() {
        viewModelScope.launch {
            reduce {
                it.copy(isLoading = true)
            }
            currencyInteractor.getCurrencies().last().rates.map { rate ->
                CurrencyUi(
                    code = rate.code,
                    title = rate.currency,
                )
            }.sortedBy { it.code }.let { currencies ->
                reduce {
                    it.copy(
                        currencies = currencies
                    )
                }
            }
        }.invokeOnCompletion {
            viewModelScope.reduce {
                it.copy(isLoading = false)
            }
        }
    }

    fun onCurrencyClick(code: String) {
        viewModelScope.sendEvent(Event.GoToCurrencyDetails(code))
    }
}