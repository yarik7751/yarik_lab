package com.joy.yariklab.features.curencydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.core.domain.interactor.CurrencyInteractor
import com.joy.yariklab.features.curencydetails.CurrencyDetailsViewModel.Event
import com.joy.yariklab.features.curencydetails.CurrencyDetailsViewModel.ViewState
import com.joy.yariklab.features.curencydetails.model.CurrencyDetailsUi
import kotlinx.coroutines.launch

class CurrencyDetailsViewModel(
    private val currencyCode: String,
    private val currencyInteractor: CurrencyInteractor,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val currencyDetailsUi: CurrencyDetailsUi? = null,
    )

    sealed interface Event

    init {
        viewModelScope.launch {
            val currencyDetailsUi = currencyInteractor.getCurrencyByCode(currencyCode).let { currencyDetails ->
                CurrencyDetailsUi(
                    code = currencyDetails.code,
                    currency = currencyDetails.currency,
                    rates = currencyDetails.rates.map { rate ->
                        CurrencyDetailsUi.Rate(
                            no = rate.no,
                            mid = rate.mid,
                            effectiveDate = rate.effectiveDate,
                        )
                    }
                )
            }

            reduce {
                it.copy(currencyDetailsUi = currencyDetailsUi)
            }
        }
    }
}