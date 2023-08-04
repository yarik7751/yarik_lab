package com.joy.yariklab.features.login

import androidx.lifecycle.ViewModel
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.login.LoginViewModel.Event
import com.joy.yariklab.features.login.LoginViewModel.ViewState

class LoginViewModel(
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
    )

    sealed interface Event
}