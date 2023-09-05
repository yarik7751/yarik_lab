package com.joy.yariklab.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.archkit.safeLaunch
import com.joy.yariklab.core.domain.interactor.SignInUpInteractor
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.login.LoginViewModel.Event
import com.joy.yariklab.features.login.LoginViewModel.ViewState
import com.joy.yariklab.toolskit.EMPTY_STRING

class LoginViewModel(
    private val signInUpInteractor: SignInUpInteractor,
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val login: String = EMPTY_STRING,
        val password: String = EMPTY_STRING,
    )

    sealed interface Event {
        object GoToUserList : Event
    }

    fun onLoginChanged(login: String) {
        viewModelScope.reduce {
            it.copy(login = login)
        }
    }

    fun onPasswordChanged(password: String) {
        viewModelScope.reduce {
            it.copy(password = password)
        }
    }

    fun onLoginAction() {
        viewModelScope.safeLaunch(errorEmitter::emit) {
            reduce {
                it.copy(isLoading = true)
            }
            signInUpInteractor.login(
                login = stateValue.login,
                password = stateValue.password,
            )

            sendEvent(Event.GoToUserList)
        }.invokeOnCompletion {
            viewModelScope.reduce {
                it.copy(isLoading = false)
            }
        }
    }
}