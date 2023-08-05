package com.joy.yariklab.features.registration

import androidx.lifecycle.ViewModel
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.registration.RegistrationViewModel.Event
import com.joy.yariklab.features.registration.RegistrationViewModel.ViewState

class RegistrationViewModel(
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
    )

    sealed interface Event
}