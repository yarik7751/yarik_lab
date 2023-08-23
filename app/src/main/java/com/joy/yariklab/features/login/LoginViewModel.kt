package com.joy.yariklab.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.archkit.safeLaunch
import com.joy.yariklab.core.domain.usecase.LoginUserUseCase
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.login.LoginViewModel.Event
import com.joy.yariklab.features.login.LoginViewModel.ViewState
import com.joy.yariklab.toolskit.EMPTY_STRING

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val login: String = EMPTY_STRING,
        val password: String = EMPTY_STRING,
    )

    sealed interface Event

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
            loginUserUseCase.execute(
                LoginUserUseCase.Params(
                    login = stateValue.login,
                    password = stateValue.password,
                )
            )
        }
    }
}