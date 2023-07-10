package com.joy.yariklab.features.curencydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.archkit.safeLaunch
import com.joy.yariklab.core.domain.interactor.CurrencyInteractor
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.curencydetails.CurrencyDetailsViewModel.Event
import com.joy.yariklab.features.curencydetails.CurrencyDetailsViewModel.ViewState
import com.joy.yariklab.features.curencydetails.model.CurrencyDetailsUi

class CurrencyDetailsViewModel(
    private val currencyCode: String,
    private val currencyInteractor: CurrencyInteractor,
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val currencyDetailsUi: CurrencyDetailsUi? = null,
    )

    sealed interface Event

    init {
        viewModelScope.safeLaunch(errorEmitter::emit) {
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