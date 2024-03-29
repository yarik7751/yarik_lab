package com.joy.yariklab.features.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.start.StartViewModel.Event
import com.joy.yariklab.features.start.StartViewModel.ViewState

class StartViewModel(
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
    )

    sealed interface Event {
        object GoToLoginScreen : Event

        object GoToRegistrationScreen : Event
    }

    fun onSignInClick() {
        viewModelScope.sendEvent(Event.GoToLoginScreen)
    }

    fun onSignUpClick() {
        viewModelScope.sendEvent(Event.GoToRegistrationScreen)
    }
}