package com.joy.yariklab.features.curencydetails

import androidx.lifecycle.ViewModel
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.core.domain.CurrencyInteractor
import com.joy.yariklab.features.curencydetails.CurrencyDetailsViewModel.Event
import com.joy.yariklab.features.curencydetails.CurrencyDetailsViewModel.ViewState

class CurrencyDetailsViewModel(
    private val currencyInteractor: CurrencyInteractor,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
    )

    sealed interface Event

    init {

    }
}